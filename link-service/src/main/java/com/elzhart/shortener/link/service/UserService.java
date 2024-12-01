package com.elzhart.shortener.link.service;

import com.elzhart.shortener.common.exception.NotFoundException;
import com.elzhart.shortener.common.exception.ValidationException;
import com.elzhart.shortener.common.model.Role;
import com.elzhart.shortener.common.model.User;
import com.elzhart.shortener.common.model.UserRole;
import com.elzhart.shortener.common.model.dao.UserRepository;
import com.elzhart.shortener.common.model.dao.UserRoleRepository;
import com.elzhart.shortener.link.api.dto.user.CreateUserRequest;

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

    @Transactional
    public User create(CreateUserRequest request) {
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

        return finalUser;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "user", key = "#username")
    public User getByUsername(String username) {
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
