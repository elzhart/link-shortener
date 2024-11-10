package com.elzhart.shortener.link.mapper;

import com.elzhart.shortener.link.api.dto.user.UserDto;
import com.elzhart.shortener.common.model.User;

import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public abstract class UserViewMapper {

    public abstract UserDto toUserView(User user);

}
