package com.wjiec.tinder.springinaction.spittr.web.controller.error;

import com.wjiec.tinder.springinaction.spittr.exception.SpitterNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class WildExceptionHandler {

    @ExceptionHandler(SpitterNotFoundException.class)
    public String handleSpitterNotFound() {
        log.info("WildExceptionHandler.handleSpitterNotFound");
        return "error/spitterNotFound";
    }

}
