package com.wjiec.springaio.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "spitter not found")
public class SpitterNotFoundException extends RuntimeException {
}
