package org.laboys.better.spring.core.web.handler;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.laboys.better.spring.core.annotation.web.RestExceptionHandler;
import org.laboys.better.spring.core.web.decoration.DecorationError;
import org.laboys.better.spring.core.web.decoration.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;

import java.util.List;
import java.util.stream.Collectors;

@RestExceptionHandler(value = BindException.class,
    statusCode = StatusCode.PARAMS_ERROR, httpStatus = HttpStatus.BAD_REQUEST)
public class ValidationBindErrorHandler implements Handler<BindException> {

    @Override
    public List<?> errors(BindException ex) {
        return ex.getFieldErrors().stream()
            .map((e) -> BindError.builder()
                .error("invalid-parameter")
                .message(e.getDefaultMessage())
                .field(e.getField())
                .build())
            .collect(Collectors.toList());
    }

    @Data
    @SuperBuilder
    @EqualsAndHashCode(callSuper = true)
    private static class BindError extends DecorationError {
        private String field;
    }

}
