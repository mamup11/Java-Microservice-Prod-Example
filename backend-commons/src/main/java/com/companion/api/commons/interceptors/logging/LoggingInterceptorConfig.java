package com.companion.api.commons.interceptors.logging;

import com.companion.api.commons.external.authfast.AuthFastService;
import com.companion.api.commons.interceptors.authentication.AuthInterceptor;
import org.apache.logging.log4j.core.lookup.MainMapLookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This class configures adds the logging interceptor to the registry so all request are automatically logged
 */
@Configuration
public class LoggingInterceptorConfig implements WebMvcConfigurer {

    private final LoggingMasker loggingMasker;
    private final AuthFastService authFastService;

    public LoggingInterceptorConfig(@Value("${application.name:}") String applicationName,
                                    AuthFastService authFastService,
                                    LoggingMasker loggingMasker) {
        MainMapLookup.setMainArguments(applicationName);
        this.loggingMasker = loggingMasker;
        this.authFastService = authFastService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor(loggingMasker)).order(Ordered.HIGHEST_PRECEDENCE);
        registry.addInterceptor(new AuthInterceptor(authFastService));
    }
}
