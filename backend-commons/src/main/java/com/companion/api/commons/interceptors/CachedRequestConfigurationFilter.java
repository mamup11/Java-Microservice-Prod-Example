package com.companion.api.commons.interceptors;

import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class CachedRequestConfigurationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // This step is needed to be able to read the request body multiple times, normally it can only be read once
        // but with this configuration it will be readable multiple times so it can be logged
        request = new CachedHttpServletRequest((HttpServletRequest) request);
        chain.doFilter(request, response);
    }
}
