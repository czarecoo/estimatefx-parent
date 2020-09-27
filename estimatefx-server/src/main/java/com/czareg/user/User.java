package com.czareg.user;

import com.czareg.dto.UserDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    public UserType getType() {
        return userType;
    }

    public void setType(UserType userType) {
        this.userType = userType;
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