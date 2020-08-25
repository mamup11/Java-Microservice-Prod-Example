package com.companion.api.commons.interceptors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;
import java.util.UUID;

@Slf4j
public class LoggingInterceptor extends HandlerInterceptorAdapter {
    private static final String X_CORRELATION_ID = "x-correlation-id";
    private static final String X_CONVERSATION_ID = "x-conversation-id";
    private static final String REQUEST_START_TIME = "requestStartTime";
    private static final String CLIENT_ADDRESS = "clientAddress";
    private static final String RESPONSE_TIME = "responseTime";
    private static final String REQUEST_BASE_LOG = "Starting Method '{}' Path '{}'";

    private static final Set<String> LOGGABLE_METHODS = Sets.newHashSet("POST", "PUT", "PATCH");

    private final ObjectMapper mapper;
    private final boolean logLevelIsInfo;
    private final boolean logLevelIsDebug;

    public LoggingInterceptor(ObjectMapper objectMapper) {
        this.mapper = objectMapper;
        this.logLevelIsInfo = log.isInfoEnabled() && !log.isDebugEnabled();
        this.logLevelIsDebug = log.isDebugEnabled();
    }

    private String getUUIDifEmpty(String value) {
        if (StringUtils.isBlank(value)) {
            value = UUID.randomUUID().toString();
        }
        return value;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String correlationId = getUUIDifEmpty(request.getHeader(X_CORRELATION_ID));
        String conversationId = getUUIDifEmpty(request.getHeader(X_CONVERSATION_ID));

        // Save the correlation and conversation IDs to the thread context to be used by the logs each time a log is written
        MDC.put(X_CORRELATION_ID, correlationId);
        MDC.put(X_CONVERSATION_ID, conversationId);
        MDC.put(CLIENT_ADDRESS, request.getRemoteHost() + ":" + request.getRemotePort());

        if (logLevelIsInfo) {
            log.info(REQUEST_BASE_LOG, request.getMethod(), request.getRequestURI());
        } else if (logLevelIsDebug) {
            if (request instanceof CachedHttpServletRequest && LOGGABLE_METHODS.contains(request.getMethod())) {
                CachedHttpServletRequest cachedRequest = (CachedHttpServletRequest) request;
                String body = cachedRequest.getBody();

                if (StringUtils.isNotBlank(body)) {
                    // This line sanitises the body string to remove white spaces and line breaks
                    body = mapper.readValue(cachedRequest.getBody(), JsonNode.class).toString();
                }

                log.debug(REQUEST_BASE_LOG + ", RequestBody: {}", request.getMethod(), request.getRequestURI(), body);
            } else {
                log.info(REQUEST_BASE_LOG, request.getMethod(), request.getRequestURI());
            }
        }

        // Save the start time of the request and the correlation and conversation ID in the request context
        request.setAttribute(REQUEST_START_TIME, System.currentTimeMillis());
        request.setAttribute(X_CORRELATION_ID, correlationId);
        request.setAttribute(X_CONVERSATION_ID, conversationId);

        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        String responseTime = String.valueOf(System.currentTimeMillis() - (Long) request.getAttribute(REQUEST_START_TIME));

        MDC.put(RESPONSE_TIME, responseTime);

        if (logLevelIsInfo) {
            log.info("Finished Method '{}' Path '{}', responseTime: {}ms", request.getMethod(), request.getRequestURI(), responseTime);
        } else if (logLevelIsDebug) {
            String responseBody = "";

            log.debug("Finished Method: '{}', Path: '{}', ResponseStatus '{}', ResponseBody: {}, responseTime: {}ms", request.getMethod(),
                    request.getRequestURI(), response.getStatus(), responseBody, responseTime);

        }
    }
}
