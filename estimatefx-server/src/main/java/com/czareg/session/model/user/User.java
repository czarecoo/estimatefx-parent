package com.czareg.session.model.user;

import com.czareg.dto.UserDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.czareg.session.model.user.UserType.CREATOR;
import static com.czareg.session.model.user.UserType.JOINER;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class User {
    private String name;
    private UserType userType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<UserDTO> mapUsersToDTOs(List<User> users) {
        return users.stream()
                .map(User::toDTO)
                .collect(Collectors.toList());
    }

    public boolean isCreator() {
        return userType == CREATOR;
    }

    public boolean isJoiner() {
        return userType == JOINER;
    }

    public void setType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", type=" + userType +
                '}';
    }

    public UserDTO toDTO() {
        return new UserDTO(name, userType.toDTO());
    }
}