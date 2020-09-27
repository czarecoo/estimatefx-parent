package com.czareg.session;

import com.czareg.session.exceptions.BadRequestException;
import com.czareg.session.exceptions.NotExistsException;
import com.czareg.user.Type;
import com.czareg.user.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.czareg.session.State.*;
import static com.czareg.user.Type.CREATOR;
import static com.czareg.user.Type.JOINER;

@Component
public class SessionService {
    private SessionRepository sessionRepository;
    private ObjectFactory<Session> sessionFactory;
    private ObjectFactory<User> userFactory;

    public SessionService(SessionRepository sessionRepository, ObjectFactory<Session> sessionFactory,
                          ObjectFactory<User> userFactory) {
        this.sessionRepository = sessionRepository;
        this.sessionFactory = sessionFactory;
        this.userFactory = userFactory;
    }

    public Session create(String userName) {
        User creator = createUser(userName, CREATOR);
        Session session = sessionFactory.getObject();
        session.addUser(creator);
        sessionRepository.add(session);
        return session;
    }

    public Session getSession(int sessionId) throws NotExistsException {
        return sessionRepository.getSession(sessionId);
    }

    public List<Session> getSessions() {
        return sessionRepository.getSessions();
    }

    public void vote(Integer sessionId, String userName, int value) throws NotExistsException, BadRequestException {
        Session session = getSession(sessionId);
        User user = findUser(userName, session);
        session.vote(user.getName(), value);
    }

    public Session join(Integer sessionId, String userName) throws NotExistsException, BadRequestException {
        Session session = getSession(sessionId);

        if (isNameTakenForSession(session, userName)) {
            throw new BadRequestException("Name is already taken. Please use different one");
        }

        User user = createUser(userName, JOINER);
        session.addUser(user);
        return session;
    }

    public void leave(Integer sessionId, String userName) throws NotExistsException {
        Session session = getSession(sessionId);
        User user = findUser(userName, session);
        session.removeUser(user);
        if (session.getUsers().isEmpty()) {
            sessionRepository.delete(sessionId);
        }
        if (didCreatorLeave(session)) {
            session.setClosedState();
        }
    }

    public void startVoting(int sessionId, String userName) throws NotExistsException, BadRequestException {
        Session session = getSession(sessionId);
        User user = findUser(userName, session);

        State currentState = session.getState();
        if (currentState == VOTING || currentState == CLOSED) {
            throw new BadRequestException("Cannot start voting in current state:" + currentState);
        }
        if (!user.getType().isCreator()) {
            throw new BadRequestException("Only creator can start voting.");
        }

        session.getVotes().clear();
        session.setVotingState();
    }

    public void stopVoting(int sessionId, String userName) throws NotExistsException, BadRequestException {
        Session session = getSession(sessionId);
        User user = findUser(userName, session);
        State currentState = session.getState();
        if (currentState == WAITING || currentState == CLOSED) {
            throw new BadRequestException("Cannot stop voting in current state:" + currentState);
        }
        if (!user.getType().isCreator()) {
            throw new BadRequestException("Only creator can stop voting.");
        }

        session.setWaitingState();
    }

    private boolean isNameTakenForSession(Session session, String userName) {
        return session.getUsers().stream()
                .map(User::getName)
                .anyMatch(name -> name.equals(userName));
    }

    private boolean didCreatorLeave(Session session) {
        return session.getUsers().stream()
                .map(User::getType)
                .noneMatch(Type::isCreator);
    }

    private User findUser(String userName, Session session) throws NotExistsException {
        return session.getUsers().stream()
                .filter(user -> user.getName().equals(userName))
                .findFirst()
                .orElseThrow(() -> new NotExistsException("User does not exist in this session"));
    }

    private User createUser(String userName, Type type) {
        User creator = userFactory.getObject();
        creator.setName(userName);
        creator.setType(type);
        return creator;
    }
}