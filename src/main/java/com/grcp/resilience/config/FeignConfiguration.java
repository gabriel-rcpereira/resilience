package com.grcp.resilience.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class FeignConfiguration {

    @Bean
    public Logger logger() {
        return new FeignLoggerTestConfiguration();
    }
}
