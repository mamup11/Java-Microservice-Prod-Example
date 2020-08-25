package com.companion.api.commons;

import com.companion.api.commons.interceptors.LoggingInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This class configures the
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {

    private final ObjectMapper objectMapper;

    public AppConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor(objectMapper));
    }
}
