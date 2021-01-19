package com.example.blog.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {

    PUBLIC("PUBLIC"), DRAFT("DRAFT");

    private String valueOfStatus;

    Status(String valueOfStatus) {
        this.valueOfStatus = valueOfStatus;
    }

    @JsonValue
    public String getValueOfStatus() {
        return valueOfStatus;
    }
}
