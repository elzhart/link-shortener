package com.elzhart.shortener.linkservice.api.dto.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AuthRequest(
        @NotNull @Email String username,
        @NotNull String password) {

    public AuthRequest() {
        this(null, null);
    }
}
