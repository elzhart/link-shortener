package com.elzhart.shortener.linkservice.model.dao;

import com.elzhart.shortener.linkservice.model.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
