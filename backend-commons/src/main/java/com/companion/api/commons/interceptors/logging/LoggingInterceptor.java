package com.companion.api.commons.interceptors.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.UUID;

@Slf4j
public class LoggingInterceptor extends HandlerInterceptorAdapter {
    private static final String X_CORRELATION_ID = "x-correlation-id";
    private static final String X_CONVERSATION_ID = "x-conversation-id";
    private static final String X_FORWARDED_FOR = "X-Forwarded-For";
    private static final String X_FORWARDED_PORT = "X-Forwarded-Port";
    private static final String REQUEST_START_TIME = "requestStartTime";
    private static final String CLIENT_ADDRESS = "clientAddress";
    private static final String RESPONSE_TIME = "responseTime";
    private static final String REQUEST_START_PREFIX_LOG = "Starting Request ";
    private static final String REQUEST_FINISH_PREFIX_LOG = "Finished Request ";

    private static final Set<String> LOGGABLE_METHODS = Sets.newHashSet("POST", "PUT", "PATCH");

    private final LoggingMasker loggingMasker;

    public LoggingInterceptor(LoggingMasker loggingMasker) {
        this.loggingMasker = loggingMasker;
    }

    private String getUUIDifEmpty(String value) {
        if (StringUtils.isBlank(value)) {
            value = UUID.randomUUID().toString();
        }
        return value;
    }

    private void logRequest(HttpServletRequest request) {
        logRequest(REQUEST_START_PREFIX_LOG, request, null);
    }

    private void logRequest(String prefix, HttpServletRequest request, HttpServletResponse response) {
        if (log.isInfoEnabled()) {
            try {
                StringBuilder builder = new StringBuilder();
                builder.append(prefix);
                builder.append(request.getMethod()).append(" ");
                builder.append(request.getRequestURI());
                String payload;
                payload = request.getQueryString();
                if (payload != null) {
                    builder.append('?').append(payload);
                }

                // If debug is enabled, its suppose to have a body and the request is a custom readable object then log the request body
                if (log.isDebugEnabled() && request instanceof CachedHttpServletRequest &&
                        LOGGABLE_METHODS.contains(request.getMethod())) {

                    CachedHttpServletRequest cachedRequest = (CachedHttpServletRequest) request;
                    builder.append(", RequestBody: ").append(loggingMasker.maskJsonMessage(cachedRequest.getBody()));
                }

                if (response != null) {
                    addResponseToMessage(builder, request, response);
                }

                if (log.isDebugEnabled()) {
                    log.debug(builder.toString());
                } else {
                    log.info(builder.toString());
                }
            } catch (JsonProcessingException | UnsupportedEncodingException ex) {
                log.error("Error creating request log", ex);
            }
        }
    }

    private void addResponseToMessage(StringBuilder builder, HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException, JsonProcessingException {
        builder.append("; ResponseStatus: ").append(response.getStatus());
        builder.append(", ResponseTime: ").append(System.currentTimeMillis() -
                (long) request.getAttribute(REQUEST_START_TIME));
        builder.append("ms");

        if (log.isDebugEnabled() && response instanceof ContentCachingResponseWrapper) {
            String responseBody = new String(((ContentCachingResponseWrapper) response).getContentAsByteArray(),
                    response.getCharacterEncoding());

            builder.append(", ResponseBody: ").append(loggingMasker.maskJsonMessage(responseBody));
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String correlationId = getUUIDifEmpty(request.getHeader(X_CORRELATION_ID));
        String conversationId = getUUIDifEmpty(request.getHeader(X_CONVERSATION_ID));
        // Since this is a microservice template I assume that there is a load balancer, to get useful information
        // from the logs we don't need that the IP of the load balancer gets logged each time, so the load balancer
        // needs to be configured to forward the client IP on the configured headers "X-Forwarded-For" and
        // "X-Forwarded-Port" if not present it will log whatever address is available as the caller. Current solutions
        // like aws load balancer support the forwarding of these headers out of the box
        String clientAddress = request.getHeader(X_FORWARDED_FOR);
        String clientPort = request.getHeader(X_FORWARDED_PORT);

        if (StringUtils.isBlank(clientAddress)) {
            clientAddress = request.getRemoteHost();
        }
        if (StringUtils.isBlank(clientPort)) {
            clientPort = String.valueOf(request.getRemotePort());
        }

        // Save the correlation and conversation IDs to the thread context to be used by the logs each time a log is written
        MDC.put(X_CORRELATION_ID, correlationId);
        MDC.put(X_CONVERSATION_ID, conversationId);
        MDC.put(CLIENT_ADDRESS, clientAddress + ":" + clientPort);

        // Log the request information
        logRequest(request);

        // Save the start time of the request and the correlation and conversation ID in the request context
        request.setAttribute(REQUEST_START_TIME, System.currentTimeMillis());
        request.setAttribute(X_CORRELATION_ID, correlationId);
        request.setAttribute(X_CONVERSATION_ID, conversationId);

        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        MDC.put(RESPONSE_TIME, String.valueOf(System.currentTimeMillis() - (long) request.getAttribute(REQUEST_START_TIME)));

        logRequest(REQUEST_FINISH_PREFIX_LOG, request, response);

        // Set the correlation and conversation IDs in the response headers so the client knows the id of its request
        response.setHeader(X_CORRELATION_ID, (String) request.getAttribute(X_CORRELATION_ID));
        response.setHeader(X_CONVERSATION_ID, (String) request.getAttribute(X_CONVERSATION_ID));
    }
}
