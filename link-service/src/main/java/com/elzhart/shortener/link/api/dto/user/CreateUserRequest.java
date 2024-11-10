package com.elzhart.shortener.link.api.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record CreateUserRequest(
        @NotBlank @Email String name,
        @NotBlank String fullName,
        @NotBlank String password,
        @NotBlank String rePassword
) {
    public CreateUserRequest(String name, String fullName, String password) {
        this(name, fullName, password, "");
    }
}
