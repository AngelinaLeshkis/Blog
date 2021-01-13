package com.example.blog.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private String valueOfRole;

    Role(String valueOfRole) {
        this.valueOfRole = valueOfRole;
    }

    @JsonValue
    public String getValueOfRole() {
        return valueOfRole;
    }

}
