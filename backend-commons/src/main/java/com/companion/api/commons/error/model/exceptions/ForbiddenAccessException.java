package com.companion.api.commons.error.model.exceptions;

public class ForbiddenAccessException extends RuntimeException {

    public ForbiddenAccessException() {
        super();
    }

    public ForbiddenAccessException(String s) {
        super(s);
    }

    public ForbiddenAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenAccessException(Throwable cause) {
        super(cause);
    }
}
