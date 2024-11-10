package com.elzhart.shortener.common.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import static jakarta.persistence.FetchType.EAGER;
import static java.util.stream.Collectors.toSet;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@With
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String fullName;

    private LocalDateTime created = LocalDateTime.now();

    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = EAGER)
    private List<UserRole> roles;


    @Override
    public Set<GrantedAuthority> getAuthorities() {
        if (roles != null) {
            return roles.stream().map(it -> (GrantedAuthority) () -> it.getRole().toString()).collect(toSet());
        }
        return new HashSet<>();
    }

    @Override
    public String getUsername() {
        return name;
    }
}
