package com.companion.api.commons.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoggingInterceptor extends HandlerInterceptorAdapter {
    private static final String X_CORRELATION_ID = "x-correlation-id";
    private static final String X_CONVERSATION_ID = "x-conversation-id";
    private static final String REQUEST_START_TIME = "requestStartTime";

    private String getTackingId(HttpServletRequest request, String headerName) {
        return null;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String correlationId = request.getHeader(X_CORRELATION_ID);
        String conversationId = request.getHeader(X_CONVERSATION_ID);

        // Save the correlation and conversation IDs to the thread context to be used by the logs each time a log is written
        MDC.put(correlationId, request.getHeader(X_CORRELATION_ID));
        MDC.put(conversationId, request.getHeader(X_CONVERSATION_ID));

        log.info("Starting Method {}, Path '{}'", request.getMethod(), request.getPathInfo());

        request.setAttribute(REQUEST_START_TIME, System.currentTimeMillis());
        request.setAttribute(X_CORRELATION_ID, correlationId);
        request.setAttribute(X_CONVERSATION_ID, conversationId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println("Request URL::" + request.getRequestURL().toString()
                + " This is POST Handler :: Time passed=" + (System.currentTimeMillis() - (Long) request.getAttribute("startTime")));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        long startTime = (Long) request.getAttribute("startTime");
        log.info("Request URL::" + request.getRequestURL().toString()
                + ":: Time Taken=" + (System.currentTimeMillis() - startTime));
    }
}
