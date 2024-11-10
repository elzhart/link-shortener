package com.elzhart.shortener.common;

import com.redis.testcontainers.RedisContainer;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public abstract class AbstractIntegrationTest {

    static final RedisContainer redisContainer = new RedisContainer("redis:7").withReuse(true);

    static final PostgreSQLContainer<?> pgContainer = new PostgreSQLContainer<>("postgres:13")
            .withReuse(true)
            .withDatabaseName("link-service")
            .withUsername("postgres");

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        redisContainer.start();
        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add("spring.data.redis.port", redisContainer::getRedisPort);
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        pgContainer.start();
        registry.add("spring.datasource.url", pgContainer::getJdbcUrl);
        registry.add("spring.datasource.username", () -> "postgres");
        registry.add("spring.datasource.password", pgContainer::getPassword);
    }
}