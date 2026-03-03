package com.fegcocco.emovebackend.config;

import com.fegcocco.emovebackend.ratelimit.RateLimitingFilter;
import com.fegcocco.emovebackend.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimitConfig {

    @Bean
    public RateLimitingFilter rateLimitingFilter(TokenService tokenService) {
        return new RateLimitingFilter(tokenService);
    }
}