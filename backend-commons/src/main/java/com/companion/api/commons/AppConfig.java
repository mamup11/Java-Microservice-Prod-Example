package com.companion.api.commons;

import com.companion.api.commons.interceptors.CachedRequestConfigurationFilter;
import com.companion.api.commons.interceptors.LoggingInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
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

    @Bean
    @ConditionalOnProperty(value = "logging.level.com.companion.api", havingValue = "debug")
    public FilterRegistrationBean<CachedRequestConfigurationFilter> loggingFilter() {
        FilterRegistrationBean<CachedRequestConfigurationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CachedRequestConfigurationFilter());

        return registrationBean;
    }
}
