package com.elzhart.shortener.link.integration.data;

import com.elzhart.shortener.link.api.dto.user.CreateUserRequest;
import com.elzhart.shortener.link.api.dto.user.UserDto;
import com.elzhart.shortener.link.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@Service
public class UserTestDataFactory {

    @Autowired
    private UserService userService;

    public UserDto createUser(
            String username,
            String fullName,
            String password) {
        CreateUserRequest createRequest = new CreateUserRequest(
                username, fullName, password, password
        );

        UserDto userDto = userService.create(createRequest);

        assertNotNull(userDto.id(), "User id must not be null!");
        assertEquals(fullName, userDto.fullName(), "User name update isn't applied!");

        return userDto;
    }
}
