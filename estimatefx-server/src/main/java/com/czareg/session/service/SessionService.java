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

import static com.czareg.session.model.State.*;
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

    public Session create(String userName) throws BadRequestException {
        User creator = createUser(userName, CREATOR);
        Session session = sessionFactory.getObject();
        session.addCreator(creator);
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
        if (didCreatorLeave(session)) {
            session.setClosedState();
        }
        inactiveUserCleaningService.remove(sessionId, userName);
        return session;
    }

    public Session startVoting(int sessionId, String userName) throws NotExistsException, BadRequestException {
        Session session = getSession(sessionId);
        User user = findUser(userName, session);
        inactiveUserCleaningService.add(sessionId, userName);

        State currentState = session.getState();
        if (currentState == VOTING || currentState == CLOSED) {
            throw new BadRequestException("Cannot start voting in current state: " + currentState);
        }
        if (!user.getType().isCreator()) {
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
        if (currentState == WAITING || currentState == CLOSED) {
            throw new BadRequestException("Cannot stop voting in current state:" + currentState);
        }
        if (!user.getType().isCreator()) {
            throw new BadRequestException("Only creator can stop voting.");
        }

        session.setWaitingState();
        return session;
    }

    private boolean isNameTakenForSession(Session session, String userName) {
        return session.getUsers().stream()
                .map(User::getName)
                .anyMatch(name -> name.equals(userName));
    }

    private boolean didCreatorLeave(Session session) {
        return session.getUsers().stream()
                .map(User::getType)
                .noneMatch(UserType::isCreator);
    }

    private User findUser(String userName, Session session) throws NotExistsException {
        return session.getUsers().stream()
                .filter(user -> user.getName().equals(userName))
                .findFirst()
                .orElseThrow(() -> new NotExistsException("User does not exist in this session"));
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