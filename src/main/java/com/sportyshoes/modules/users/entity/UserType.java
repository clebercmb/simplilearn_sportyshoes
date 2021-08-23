package com.sportyshoes.modules.users.entity;

public enum UserType {
    ADMIN("ADMIN"),
    USER("USER");

    public final String userType;

    UserType(String userType) {
        this.userType = userType;
    }



}
