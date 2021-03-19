package org.laboys.better.spring.core.web;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public final class RestExceptionAdvice {

    @ExceptionHandler(Throwable.class)
    public Object exceptionHandler(Throwable ex) {
        // @TODO
        return ex;
    }

}
