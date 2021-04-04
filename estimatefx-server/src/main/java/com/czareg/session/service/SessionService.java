package com.czareg.session.service;

import com.czareg.session.exceptions.BadRequestException;
import com.czareg.session.exceptions.NotExistsException;
import com.czareg.session.model.Session;
import com.czareg.session.model.State;
import com.czareg.session.model.user.User;
import com.czareg.session.model.user.UserType;
import com.czareg.session.repository.SessionRepository;
import com.czareg.validator.UserNameValidator;
import com.czareg.validator.ValidationResult;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.czareg.session.model.State.VOTING;
import static com.czareg.session.model.State.WAITING;
import static com.czareg.session.model.user.UserType.CREATOR;
import static com.czareg.session.model.user.UserType.JOINER;

@Component
public class SessionService {
    private SessionRepository sessionRepository;
    private ObjectFactory<Session> sessionFactory;
    private ObjectFactory<User> userFactory;
    private InactiveUserCleaningService inactiveUserCleaningService;

    public SessionService(SessionRepository sessionRepository, ObjectFactory<Session> sessionFactory,
                          ObjectFactory<User> userFactory, InactiveUserCleaningService inactiveUserCleaningService) {
        this.sessionRepository = sessionRepository;
        this.sessionFactory = sessionFactory;
        this.userFactory = userFactory;
        this.inactiveUserCleaningService = inactiveUserCleaningService;
    }

    public Session create(String userName, boolean allowPassingCreator, boolean allowStealingCreator,
                          boolean passCreatorWhenLeaving) throws BadRequestException {
        User creator = createUser(userName, CREATOR);
        Session session = sessionFactory.getObject();
        session.addCreator(creator);
        session.setAllowPassingCreator(allowPassingCreator);
        session.setAllowStealingCreator(allowStealingCreator);
        session.setPassCreatorWhenLeaving(passCreatorWhenLeaving);
        sessionRepository.add(session);
        inactiveUserCleaningService.add(session.getSessionId(), userName);
        return session;
    }

    public Session getSession(int sessionId) throws NotExistsException {
        return sessionRepository.getSession(sessionId);
    }

    public List<Session> getSessions() {
        return sessionRepository.getSessions();
    }

    public Session vote(Integer sessionId, String userName, String voteValue) throws NotExistsException, BadRequestException {
        Session session = getSession(sessionId);
        User user = findUser(userName, session);
        inactiveUserCleaningService.add(sessionId, userName);
        session.vote(user, voteValue);
        return session;
    }

    public Session join(Integer sessionId, String userName) throws NotExistsException, BadRequestException {
        Session session = getSession(sessionId);

        if (isNameTakenForSession(session, userName)) {
            throw new BadRequestException("Name is already taken. Please use different one");
        }

        User user = createUser(userName, JOINER);
        session.addJoiner(user);
        inactiveUserCleaningService.add(sessionId, userName);
        return session;
    }

    public Session leave(Integer sessionId, String userName) throws NotExistsException {
        Session session = getSession(sessionId);
        User user = findUser(userName, session);
        session.removeUser(user);
        if (user.isCreator() && session.isPassCreatorWhenLeaving()) {
            makeNextUserCreatorIfPossible(session);
        }
        inactiveUserCleaningService.remove(sessionId, userName);
        return session;
    }

    public Session startVoting(int sessionId, String userName) throws NotExistsException, BadRequestException {
        Session session = getSession(sessionId);
        User user = findUser(userName, session);
        inactiveUserCleaningService.add(sessionId, userName);

        State currentState = session.getState();
        if (currentState == VOTING) {
            throw new BadRequestException("Session is already in voting state");
        }
        if (!user.isCreator()) {
            throw new BadRequestException("Only creator can start voting.");
        }

        session.getUserVotes().clearVotes();
        session.setVotingState();
        return session;
    }

    public Session stopVoting(int sessionId, String userName) throws NotExistsException, BadRequestException {
        Session session = getSession(sessionId);
        User user = findUser(userName, session);
        inactiveUserCleaningService.add(sessionId, userName);

        State currentState = session.getState();
        if (currentState == WAITING) {
            throw new BadRequestException("Session is already in waiting state");
        }
        if (!user.isCreator()) {
            throw new BadRequestException("Only creator can stop voting.");
        }

        session.setWaitingState();
        return session;
    }

    public Session passCreator(int sessionId, String oldCreatorUserName, String newCreatorUserName)
            throws NotExistsException, BadRequestException {
        Session session = getSession(sessionId);
        if (!session.isAllowPassingCreator()) {
            throw new BadRequestException("Passing creator is turned off for this session");
        }
        User oldCreator = findUser(oldCreatorUserName, session);
        User newCreator = findUser(newCreatorUserName, session);
        if (!oldCreator.isCreator()) {
            throw new BadRequestException("Old creator is not a creator");
        }
        if (newCreator.isCreator()) {
            throw new BadRequestException("New creator is already a creator");
        }
        oldCreator.setType(JOINER);
        newCreator.setType(CREATOR);
        return session;
    }

    public Session stealCreator(int sessionId, String userName) throws NotExistsException, BadRequestException {
        Session session = getSession(sessionId);
        if (!session.isAllowStealingCreator()) {
            throw new BadRequestException("Stealing creator is turned off for this session");
        }
        Optional<User> oldCreator = findCreator(session);
        User newCreator = findUser(userName, session);
        if (newCreator.isCreator()) {
            throw new BadRequestException("User is already a creator");
        }
        oldCreator.ifPresent(user -> user.setType(JOINER));
        newCreator.setType(CREATOR);
        return session;
    }

    public Session kickUser(int sessionId, String userName, String userToKick) throws NotExistsException, BadRequestException {
        Session session = getSession(sessionId);
        User creator = findUser(userName, session);
        if (!creator.isCreator()) {
            throw new BadRequestException("User " + userName + " is not creator");
        }
        User userToBeKicked = findUser(userToKick, session);
        if (userToBeKicked.isCreator()) {
            throw new BadRequestException("User " + userToKick + " is creator and cannot be kicked");
        }
        session.removeUser(userToBeKicked);
        return session;
    }

    private void makeNextUserCreatorIfPossible(Session session) {
        session.getUsers().stream()
                .filter(User::isJoiner)
                .findFirst()
                .ifPresent(newCreator -> newCreator.setType(CREATOR));
    }

    private boolean isNameTakenForSession(Session session, String userName) {
        return session.getUsers().stream()
                .map(User::getName)
                .anyMatch(name -> name.equals(userName));
    }

    private User findUser(String userName, Session session) throws NotExistsException {
        return session.getUsers().stream()
                .filter(user -> user.getName().equals(userName))
                .findFirst()
                .orElseThrow(() -> new NotExistsException("User does not exist in this session"));
    }

    private Optional<User> findCreator(Session session) {
        return session.getUsers().stream()
                .filter(User::isCreator)
                .findFirst();
    }

    private User createUser(String userName, UserType userType) throws BadRequestException {
        ValidationResult validationResult = new UserNameValidator().validate(userName);
        if (!validationResult.isSuccessful()) {
            throw new BadRequestException(validationResult.getResult());
        }
        User creator = userFactory.getObject();
        creator.setName(userName);
        creator.setType(userType);
        return creator;
    }
}