package com.elzhart.shortener.link.config;

import com.elzhart.shortener.common.config.CommonDaoConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {CommonDaoConfig.class})
public class ExternalConfig {

}
