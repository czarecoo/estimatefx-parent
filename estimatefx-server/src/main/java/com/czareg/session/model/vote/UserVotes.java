package com.czareg.session.model.vote;

import com.czareg.dto.VoteDTO;
import com.czareg.session.model.user.User;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Data
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

    public List<VoteDTO> toVoteDTO(Function<Vote, String> function) {
        return userVotes.entrySet().stream()
                .map(entry -> {
                    User user = entry.getKey();
                    Vote vote = entry.getValue();
                    String voteValue = function.apply(vote);
                    return new VoteDTO(user.toDTO(), voteValue);
                })
                .collect(toList());
    }
}