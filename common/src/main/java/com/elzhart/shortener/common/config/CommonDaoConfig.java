package com.elzhart.shortener.common.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.elzhart.shortener.common.model.dao"})
@EntityScan(basePackages = {"com.elzhart.shortener.common.model"})
public class CommonDaoConfig {
}