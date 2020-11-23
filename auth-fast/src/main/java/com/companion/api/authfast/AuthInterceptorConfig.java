package com.companion.api.authfast;

import com.companion.api.commons.interceptors.logging.LoggingInterceptor;
import com.companion.api.commons.interceptors.logging.LoggingMasker;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthInterceptorConfig implements WebMvcConfigurer {

    private final LoggingMasker loggingMasker;

    public AuthInterceptorConfig(@Value("${masker.attributesToMask:}") String comaSeparatedAttributes,
                                 ObjectMapper objectMapper) {
        this.loggingMasker = new LoggingMasker(comaSeparatedAttributes, objectMapper);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor(loggingMasker)).order(Ordered.HIGHEST_PRECEDENCE);
    }
}
