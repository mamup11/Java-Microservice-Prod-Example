package com.companion.api.commons.interceptors.authentication;

import com.companion.api.commons.error.model.exceptions.UnauthorizedException;
import com.companion.api.commons.external.authfast.AuthFastService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.companion.api.commons.utils.Context.AUTHORIZATION;

@Slf4j
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private final AuthFastService authFastService;

    public AuthInterceptor(AuthFastService authFastService) {
        this.authFastService = authFastService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader(AUTHORIZATION);

        if (StringUtils.isBlank(token)) {
            throw new UnauthorizedException();
        }

        // Set the auth token in the request context to be accessible when calling other services
        request.setAttribute(AUTHORIZATION, token);

        // Call the auth fast service to validate the token
        long startingMillis = System.currentTimeMillis();
        boolean authenticated = authFastService.validateToken();
        log.info("Auth-Fast took {}ms validating token", System.currentTimeMillis() - startingMillis);

        if (!authenticated) {
            throw new UnauthorizedException();
        }

        return super.preHandle(request, response, handler);
    }
}
