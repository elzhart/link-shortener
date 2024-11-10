package com.elzhart.shortener.redirect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@EnableCaching
@SpringBootApplication
@ComponentScan(basePackages = {"com.elzhart.shortener"})
public class RedirectServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedirectServiceApplication.class, args);
    }
}
