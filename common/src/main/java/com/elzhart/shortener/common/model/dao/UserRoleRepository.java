package com.elzhart.shortener.common.model.dao;

import com.elzhart.shortener.common.model.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
