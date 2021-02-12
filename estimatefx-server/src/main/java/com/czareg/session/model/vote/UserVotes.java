package com.czareg.session.model.vote;

import com.czareg.session.model.user.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class UserVotes {
    private Map<User, Vote> userVotes = new LinkedHashMap<>();

    public void addUser(User user) {
        Vote notVoted = new Vote();
        userVotes.put(user, notVoted);
    }

    public void removeUser(User user) {
        userVotes.remove(user);
    }

    public void addVote(User user, String voteValue) {
        removeUser(user);
        Vote vote = new Vote(voteValue);
        userVotes.put(user, vote);
    }

    public void clearVotes() {
        userVotes.replaceAll(((user, vote) -> new Vote()));
    }

    public boolean isEmpty() {
        return userVotes.keySet().isEmpty();
    }

    public Set<User> getUsers() {
        return userVotes.keySet();
    }

    public Map<String, String> getHiddenMap() {
        return getMap(Vote::getVoteState);
    }

    public Map<String, String> getNormalMap() {
        return getMap(Vote::getVoteValue);
    }

    private Map<String, String> getMap(Function<Vote, String> function) {
        Map<String, String> newVoteMap = new LinkedHashMap<>();
        for (Map.Entry<User, Vote> entry : userVotes.entrySet()) {
            String userName = entry.getKey().getName();
            String vote = function.apply(entry.getValue());
            newVoteMap.put(userName, vote);
        }
        return newVoteMap;
    }

    @Override
    public String toString() {
        return userVotes.toString();
    }
}