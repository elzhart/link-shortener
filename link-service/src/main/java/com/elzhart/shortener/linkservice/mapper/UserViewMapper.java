package com.elzhart.shortener.linkservice.mapper;

import com.elzhart.shortener.linkservice.api.dto.user.UserDto;
import com.elzhart.shortener.linkservice.model.User;

import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public abstract class UserViewMapper {

    public abstract UserDto toUserView(User user);

}
