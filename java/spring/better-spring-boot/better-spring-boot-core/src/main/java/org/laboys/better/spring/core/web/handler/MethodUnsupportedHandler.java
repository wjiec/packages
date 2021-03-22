package org.laboys.better.spring.core.web.handler;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.laboys.better.spring.core.annotation.web.RestExceptionHandler;
import org.laboys.better.spring.core.web.decoration.DecorationError;
import org.laboys.better.spring.core.web.decoration.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.List;

@RestExceptionHandler(value = HttpRequestMethodNotSupportedException.class,
    statusCode = StatusCode.METHOD_ERROR, httpStatus = HttpStatus.METHOD_NOT_ALLOWED)
public class MethodUnsupportedHandler implements Handler<HttpRequestMethodNotSupportedException> {

    @Override
    public List<?> errors(HttpRequestMethodNotSupportedException ex) {
        return List.of(MethodError.builder()
            .error("method-not-Allowed")
            .message("Method-Not-Allowed")
            .currentMethod(ex.getMethod())
            .supportedMethods(ex.getSupportedMethods())
            .build());
    }

    @Data
    @SuperBuilder
    @EqualsAndHashCode(callSuper = true)
    private static class MethodError extends DecorationError {
        private String currentMethod;
        private String[] supportedMethods;
    }

}
