package org.laboys.better.spring.core.web.handler;

import org.laboys.better.spring.core.annotation.web.RestExceptionHandler;
import org.laboys.better.spring.core.web.decoration.DecorationError;
import org.laboys.better.spring.core.web.decoration.StatusCode;

import java.util.Collections;
import java.util.List;

@RestExceptionHandler(value = Throwable.class, statusCode = StatusCode.INTERNAL_ERROR)
public class ProductionExceptionHandler implements Handler<Throwable> {

    private final List<DecorationError> DEFAULT_ERRORS = Collections.singletonList(DecorationError.builder()
        .error("internal-error").message("Server Internal Error").build());

    @Override
    public List<? extends DecorationError> errors(Throwable ex) {
        return DEFAULT_ERRORS;
    }

}
