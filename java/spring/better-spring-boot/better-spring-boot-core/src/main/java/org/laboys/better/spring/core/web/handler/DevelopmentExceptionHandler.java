package org.laboys.better.spring.core.web.handler;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.laboys.better.spring.core.annotation.web.RestExceptionHandler;
import org.laboys.better.spring.core.web.decoration.DecorationError;
import org.laboys.better.spring.core.web.decoration.StatusCode;

import java.util.Collections;
import java.util.List;

@RestExceptionHandler(value = Throwable.class, statusCode = StatusCode.INTERNAL_ERROR)
public class DevelopmentExceptionHandler implements Handler<Throwable> {

    @Override
    public List<?> errors(Throwable ex) {
        return Collections.singletonList(DevelopmentError.builder()
            .error(ex.getClass().getName())
            .message(ex.getMessage())
            .stacktrace(ex.getStackTrace())
            .build());
    }

    @Data
    @SuperBuilder
    @EqualsAndHashCode(callSuper = true)
    private static class DevelopmentError extends DecorationError {
        private StackTraceElement[] stacktrace;
    }

}
