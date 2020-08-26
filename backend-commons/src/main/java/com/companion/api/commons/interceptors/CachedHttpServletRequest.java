package com.companion.api.commons.interceptors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.stream.Collectors;

public class CachedHttpServletRequest extends HttpServletRequestWrapper {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private String cachedBody;

    public CachedHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);

        // This line sanitises the body string to remove white spaces and line breaks
        this.cachedBody = OBJECT_MAPPER.readValue(request.getReader()
                        .lines()
                        .collect(Collectors.joining(System.lineSeparator())),
                JsonNode.class).toString();
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cachedBody.getBytes());
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new StringReader(cachedBody));
    }

    public String getBody() {
        return cachedBody;
    }
}
