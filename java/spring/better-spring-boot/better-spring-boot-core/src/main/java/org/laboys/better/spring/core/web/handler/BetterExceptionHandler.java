package org.laboys.better.spring.core.web.handler;

import org.laboys.better.spring.core.annotation.web.RestExceptionHandler;
import org.laboys.better.spring.core.exception.BetterException;
import org.laboys.better.spring.core.web.decoration.DecorationError;
import org.laboys.better.spring.core.web.decoration.StatusCode;

import java.util.List;

@RestExceptionHandler(value = BetterException.class, statusCode = StatusCode.UNASSIGNED_ERROR)
public class BetterExceptionHandler implements Handler<BetterException> {

    /**
     * 错误内容列表
     */
    @Override
    public List<?> errors(BetterException ex) {
        return List.of(DecorationError.builder().error(ex.getCode()).message(ex.getMessage()).build());
    }

}
