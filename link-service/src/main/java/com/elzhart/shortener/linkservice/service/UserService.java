package com.elzhart.shortener.linkservice.service;

import com.elzhart.shortener.linkservice.api.dto.user.CreateUserRequest;
import com.elzhart.shortener.linkservice.api.dto.user.UserDto;
import com.elzhart.shortener.linkservice.exception.NotFoundException;
import com.elzhart.shortener.linkservice.exception.ValidationException;
import com.elzhart.shortener.linkservice.mapper.UserViewMapper;
import com.elzhart.shortener.linkservice.model.Role;
import com.elzhart.shortener.linkservice.model.User;
import com.elzhart.shortener.linkservice.model.UserRole;
import com.elzhart.shortener.linkservice.model.dao.UserRepository;
import com.elzhart.shortener.linkservice.model.dao.UserRoleRepository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;

import lombok.RequiredArgsConstructor;

import static java.lang.String.format;

@Service
@CacheConfig(cacheNames = "users")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserViewMapper userViewMapper;

    @Transactional
    public UserDto create(CreateUserRequest request) {
        if (userRepository.findByName(request.name()).isPresent()) {
            throw new ValidationException("Username exists!");
        }
        if (!request.password().equals(request.rePassword())) {
            throw new ValidationException("Passwords don't match!");
        }

        var user = new User()
                .withName(request.name())
                .withFullName(request.fullName())
                .withPassword(passwordEncoder.encode(request.password()))
                .withCreated(LocalDateTime.now());
        user = userRepository.save(user);

        User finalUser = user;
        Arrays.stream(Role.values())
                .map(it -> new UserRole().withUser(finalUser).withRole(it))
                .forEach(userRoleRepository::save);

        return userViewMapper.toUserView(user);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "user", key = "#username")
    public User findByUsername(String username) {
        return userRepository.findByName(username).orElseThrow(() -> new NotFoundException(User.class, username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByName(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(format("User with username - %s, not found", username)));
    }
}
