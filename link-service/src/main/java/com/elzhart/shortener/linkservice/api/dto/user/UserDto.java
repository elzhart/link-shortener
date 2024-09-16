package com.elzhart.shortener.linkservice.api.dto.user;

public record UserDto(
        String id,
        String name,
        String fullName
) {
}
