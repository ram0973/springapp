package com.me.springapp.model;

public enum RoleEnum {

    ROLE_USER("user"),
    ROLE_MODERATOR("moderator"),
    ROLE_ADMIN("admin");

    public final String name;

    private RoleEnum(String name) {
        this.name = name;
    }
}
