package com.me.springapp.model;

public enum Role {
    ROLE_USER("User"),
    ROLE_MODERATOR("Moderator"),
    ROLE_ADMIN("Admin");

    public final String label;

    private Role(String label) {
        this.label = label;
    }
}
