package com.czareg.session.model.user;

import com.czareg.dto.UserDTO;
import com.czareg.dto.UserType;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Data
@Component
@Scope(SCOPE_PROTOTYPE)
public class User {
    private String name;
    private UserType userType;

    public boolean isCreator() {
        return getUserType().isCreator();
    }

    public boolean isJoiner() {
        return getUserType().isJoiner();
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

    public UserDTO toDTO() {
        return new UserDTO(name, userType);
    }
}