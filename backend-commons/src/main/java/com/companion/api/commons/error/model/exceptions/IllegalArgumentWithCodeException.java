package com.companion.api.commons.error.model.exceptions;

public class IllegalArgumentWithCodeException extends ExceptionWithCode {
    public IllegalArgumentWithCodeException(String message, String errorCode) {
        super(message, errorCode);
    }
}
