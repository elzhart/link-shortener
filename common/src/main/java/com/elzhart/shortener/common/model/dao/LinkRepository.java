package com.elzhart.shortener.common.model.dao;

import com.elzhart.shortener.common.model.Link;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findByUrl(String url);
}
