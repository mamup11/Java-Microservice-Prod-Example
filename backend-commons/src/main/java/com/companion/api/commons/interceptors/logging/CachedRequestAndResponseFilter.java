package com.companion.api.commons.interceptors.logging;

import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This calls configures the request and response to be cached so we can read the request and response as many times
 * as we want for logging purposes
 */
@Component
public class CachedRequestAndResponseFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // This step is needed to be able to read the request/response body multiple times, normally it can only be read once
        // but with this configuration it will be readable multiple times so it can be logged
        request = new CachedHttpServletRequest((HttpServletRequest) request);
        response = new ContentCachingResponseWrapper((HttpServletResponse) response);
        chain.doFilter(request, response);

        // This line sets the response body back to normal so it is not empty after being read for logging
        ((ContentCachingResponseWrapper) response).copyBodyToResponse();
    }
}
