package com.czareg.dto;

public class UserDTO {
    private String userName;
    private UserTypeDTO userType;

    public UserDTO() {
    }

    public UserDTO(String userName, UserTypeDTO userType) {
        this.userName = userName;
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserTypeDTO getUserType() {
        return userType;
    }

    public void setUserType(UserTypeDTO userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userName='" + userName + '\'' +
                ", userType=" + userType +
                '}';
    }
}
