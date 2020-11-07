package com.czareg.session.model.user;

import com.czareg.dto.UserTypeDTO;

public enum UserType {
    CREATOR, JOINER;

    public boolean isCreator() {
        return this.equals(CREATOR);
    }

    public UserTypeDTO toDTO() {
        return UserTypeDTO.valueOf(this.name());
    }
}