package com.me.springapp.model;

public enum Role {
    ROLE_USER("USER"),
    ROLE_MODERATOR("MODERATOR"),
    ROLE_ADMIN("ADMIN");

    public final String label;

    private Role(String label) {
        this.label = label;
    }
}
