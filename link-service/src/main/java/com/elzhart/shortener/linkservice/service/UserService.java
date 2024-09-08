package com.elzhart.shortener.linkservice.service;

import com.elzhart.shortener.linkservice.exception.NotFoundException;
import com.elzhart.shortener.linkservice.model.User;
import com.elzhart.shortener.linkservice.model.dao.UserRepository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.RequiredArgsConstructor;

import static java.lang.String.format;

@Service
@CacheConfig(cacheNames = "users")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "userList")
    public List<User> getAllUsers() {
        return userRepository.findAll();
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
