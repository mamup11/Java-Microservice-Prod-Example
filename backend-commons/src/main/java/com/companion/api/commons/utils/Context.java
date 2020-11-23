package com.companion.api.commons.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@SuppressWarnings("unused")
public class Context {
    public static final String AUTHORIZATION = "Authorization";
    public static final String X_CORRELATION_ID = "x-correlation-id";
    public static final String X_CONVERSATION_ID = "x-conversation-id";
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";
    public static final String X_FORWARDED_PORT = "X-Forwarded-Port";
    public static final String REQUEST_START_TIME = "requestStartTime";
    public static final String CLIENT_ADDRESS = "clientAddress";
    public static final String RESPONSE_TIME = "responseTime";

    private Context() {
    }

    public static Optional<String> getCorrelationId() {
        return getAttributeFromRequest(X_CORRELATION_ID);
    }

    public static Optional<String> getConversationId() {
        return getAttributeFromRequest(X_CONVERSATION_ID);
    }

    public static Optional<String> getAuthorization() {
        return getAttributeFromRequest(AUTHORIZATION);
    }

    public static Optional<String> getAttributeFromRequest(String attributeName) {
        Optional<HttpServletRequest> httpServletRequestOpt = getCurrentHttpRequest();

        Optional<String> attribute = Optional.empty();
        if (httpServletRequestOpt.isPresent()) {
            attribute = Optional.ofNullable((String) httpServletRequestOpt.get().getAttribute(attributeName));
        }

        return attribute;
    }

    private static Optional<HttpServletRequest> getCurrentHttpRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(requestAttributes -> ServletRequestAttributes.class.isAssignableFrom(requestAttributes.getClass()))
                .map(requestAttributes -> ((ServletRequestAttributes) requestAttributes))
                .map(ServletRequestAttributes::getRequest);
    }
}
