package com.example.blog.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ResetPasswordDTO {

    @NotBlank(message = "Email is a required field")
    @Pattern(regexp = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
