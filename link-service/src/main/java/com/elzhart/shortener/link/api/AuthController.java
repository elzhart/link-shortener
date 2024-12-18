package com.elzhart.shortener.link.api;

import com.elzhart.shortener.common.model.User;
import com.elzhart.shortener.link.api.dto.user.AuthRequest;
import com.elzhart.shortener.link.api.dto.user.CreateUserRequest;
import com.elzhart.shortener.link.api.dto.user.UserDto;
import com.elzhart.shortener.link.mapper.UserViewMapper;
import com.elzhart.shortener.link.service.UserService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;


@RestController
@RequestMapping(path = "/api/public")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final UserViewMapper userViewMapper;
    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid AuthRequest request) {
        try {
            var authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(request.username(), request.password()));

            var user = (User) authentication.getPrincipal();

            var now = Instant.now();
            var expiry = 36000L;

            var scope =
                    authentication.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(joining(" "));

            var claims =
                    JwtClaimsSet.builder()
                            .issuer("example.io")
                            .issuedAt(now)
                            .expiresAt(now.plusSeconds(expiry))
                            .subject(format(user.getUsername()))
                            .claim("roles", scope)
                            .build();

            var token = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .body(userViewMapper.toUserView(user));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("register")
    public UserDto register(@RequestBody @Valid CreateUserRequest request) {
        return userViewMapper.toUserView(userService.create(request));
    }
}
