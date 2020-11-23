package com.companion.api.products;

import com.companion.api.commons.external.authfast.AuthFastService;
import com.companion.api.commons.interceptors.authentication.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthInterceptorConfig implements WebMvcConfigurer {

    private final AuthFastService authFastService;

    @Autowired
    public AuthInterceptorConfig(AuthFastService authFastService) {
        this.authFastService = authFastService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(authFastService));
    }
}
