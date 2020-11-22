package com.companion.api.commons.error.model.exceptions;

import lombok.Getter;

@Getter
public class ExceptionWithCode extends RuntimeException {
    private final String errorCode;

    public ExceptionWithCode(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ExceptionWithCode(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ExceptionWithCode(Throwable cause, String errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }
}
