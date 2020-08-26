package com.companion.api.commons.interceptors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This class configures adds the logging interceptor to the registry so all request are automatically logged
 */
@Configuration
public class InterceptorsConfig implements WebMvcConfigurer {

    private final LoggingMasker loggingMasker;

    public InterceptorsConfig(LoggingMasker loggingMasker) {
        this.loggingMasker = loggingMasker;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor(loggingMasker));
    }
}
