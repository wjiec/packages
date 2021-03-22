package org.laboys.better.spring.core.web;

import org.laboys.better.spring.core.annotation.web.RestException;
import org.laboys.better.spring.core.annotation.web.RestExceptionHandler;
import org.laboys.better.spring.core.web.decoration.DecorationResult;
import org.laboys.better.spring.core.web.decoration.StatusCode;
import org.laboys.better.spring.core.web.handler.Handler;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public final class RestExceptionAdvice {

    /**
     * 带有特殊处理方式的异常处理器
     */
    private final Map<Class<?>, Handler<Throwable>> handlers = new HashMap<>();

    /**
     * 默认异常处理器
     */
    private static final Handler<Throwable> defaultExceptionHandler = new DefaultExceptionHandler();

    @SuppressWarnings("unchecked")
    public RestExceptionAdvice(ApplicationContext context) {
        for (var handle : context.getBeansWithAnnotation(RestExceptionHandler.class).values()) {
            if (handle instanceof Handler) {
                RestExceptionHandler annotation = handle.getClass().getAnnotation(RestExceptionHandler.class);
                handlers.put(annotation.value(), (Handler<Throwable>) handle);
            }
        }
    }

    @ExceptionHandler(Throwable.class)
    public Object exceptionHandler(Throwable ex) {
        Handler<Throwable> handler = defaultExceptionHandler;
        for (Class<?> curr = ex.getClass(); curr != Object.class; curr = curr.getSuperclass()) {
            if (handlers.containsKey(curr)) {
                handler = handlers.get(curr);
                break;
            }
        }
        RestExceptionHandler restExceptionHandler = handler.getClass().getAnnotation(RestExceptionHandler.class);

        HttpStatus httpStatus = restExceptionHandler.httpStatus();
        if (ex.getClass().isAnnotationPresent(ResponseStatus.class)) {
            httpStatus = ex.getClass().getAnnotation(ResponseStatus.class).value();
        }

        StatusCode statusCode = restExceptionHandler.statusCode();
        RestException restException = ex.getClass().getAnnotation(RestException.class);
        if (restException != null) {
            statusCode = restException.value();
        }

        return ResponseEntity.status(httpStatus)
            .body(DecorationResult.error(statusCode, handler.errors(ex), new HashMap<>()));
    }

    @RestExceptionHandler(value = Throwable.class, statusCode = StatusCode.INTERNAL_ERROR)
    private static class DefaultExceptionHandler implements Handler<Throwable> {
        @Override
        public List<?> errors(Throwable ex) {
            return Collections.emptyList();
        }
    }

}
