package com.companion.api.products.loggin.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class Context {


    public static Optional<String> getCorrelationId() {
        Optional<HttpServletRequest> httpServletRequestOpt = getCurrentHttpRequest();

        httpServletRequestOpt.ifPresent(httpServletRequest -> {
            httpServletRequest.getHeader("X-Correlation-Id");
        });
        return Optional.empty();
    }

    private static Optional<HttpServletRequest> getCurrentHttpRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(requestAttributes -> ServletRequestAttributes.class.isAssignableFrom(requestAttributes.getClass()))
                .map(requestAttributes -> ((ServletRequestAttributes) requestAttributes))
                .map(ServletRequestAttributes::getRequest);
    }
}
