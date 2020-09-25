package com.companion.api.commons.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@SuppressWarnings("unused")
public class Context {

    private Context() {
    }

    public static Optional<String> getCorrelationId() {
        Optional<HttpServletRequest> httpServletRequestOpt = getCurrentHttpRequest();

        Optional<String> correlationId = Optional.empty();
        if (httpServletRequestOpt.isPresent()) {
            correlationId = Optional.of(httpServletRequestOpt.get().getHeader("X-Correlation-Id"));
        }

        return correlationId;
    }

    private static Optional<HttpServletRequest> getCurrentHttpRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(requestAttributes -> ServletRequestAttributes.class.isAssignableFrom(requestAttributes.getClass()))
                .map(requestAttributes -> ((ServletRequestAttributes) requestAttributes))
                .map(ServletRequestAttributes::getRequest);
    }
}
