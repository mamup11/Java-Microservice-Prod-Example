package com.companion.api.commons.error;

import com.companion.api.commons.error.model.ApiError;
import com.companion.api.commons.error.model.exceptions.ExceptionWithCode;
import com.companion.api.commons.error.model.exceptions.ForbiddenAccessException;
import com.companion.api.commons.error.model.exceptions.IllegalArgumentWithCodeException;
import com.companion.api.commons.error.model.exceptions.NotFoundException;
import com.companion.api.commons.error.model.exceptions.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    @Value("${api.enableStack-trace:false}")
    private boolean stackTraceEnabled;

    @ExceptionHandler({Exception.class})
    protected <E extends Exception> ResponseEntity<Object> handleErrorResponse(E ex) {

        ApiError.ApiErrorBuilder apiErrorBuilder = ApiError.builder()
                .timestamp(Instant.now())
                .message(ex.getMessage())
                .stackTrace(stackTraceEnabled ? ExceptionUtils.getStackTrace(ex) : null);

        HttpStatus responseStatus;
        if(ex instanceof IllegalArgumentWithCodeException) {
            responseStatus = HttpStatus.BAD_REQUEST;
            apiErrorBuilder.errorCode(((ExceptionWithCode) ex).getErrorCode());
        } else if (ex instanceof IllegalArgumentException
                || ex instanceof NotFoundException
                || ex instanceof IllegalStateException) {
            responseStatus = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof ForbiddenAccessException) {
            responseStatus = HttpStatus.FORBIDDEN;
        } else if (ex instanceof UnauthorizedException) {
            responseStatus = HttpStatus.UNAUTHORIZED;
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            responseStatus = HttpStatus.METHOD_NOT_ALLOWED;
        } else {
            log.error("Application responded with 500 error", ex);
            responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        apiErrorBuilder.status(responseStatus.value());

        return new ResponseEntity<>(apiErrorBuilder.build(), new HttpHeaders(), responseStatus);
    }
}
