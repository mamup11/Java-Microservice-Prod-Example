package com.companion.api.commons.error;

import com.companion.api.commons.error.model.ApiError;
import com.companion.api.commons.error.model.exceptions.ForbiddenAccessException;
import com.companion.api.commons.error.model.exceptions.NotFoundException;
import com.companion.api.commons.error.model.exceptions.UnauthorizedException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ErrorHandler {

    @Value("${api.enableStack-trace:false}")
    private boolean stackTraceEnabled;

    @ExceptionHandler({Exception.class})
    protected <E extends Exception> ResponseEntity<Object> handleErrorResponse(E ex) {

        ApiError.ApiErrorBuilder apiErrorBuilder = ApiError.builder()
                .timestamp(Instant.now())
                .message(ExceptionUtils.getMessage(ex))
                .stackTrace(stackTraceEnabled ? ExceptionUtils.getStackTrace(ex) : null);

        HttpStatus responseStatus;
        if (ex instanceof IllegalArgumentException) {
            responseStatus = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof NotFoundException) {
            responseStatus = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof IllegalStateException) {
            responseStatus = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof ForbiddenAccessException) {
            responseStatus = HttpStatus.FORBIDDEN;
        } else if (ex instanceof UnauthorizedException) {
            responseStatus = HttpStatus.UNAUTHORIZED;
        } else {
            responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        apiErrorBuilder.status(responseStatus.value());

        return new ResponseEntity<>(apiErrorBuilder.build(), new HttpHeaders(), responseStatus);
    }
}
