package com.wjiec.springaio.rest.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class AnimalController {

    @GetMapping("/animal/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> kind(@PathVariable String name, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/animal/dog/").path(name).build().toUri());

        return new ResponseEntity<>(name, headers, HttpStatus.CREATED);
    }

    // no ResponseBody
    @GetMapping("/animal/dog/{name}")
    public ResponseEntity<String> dogName(@PathVariable String name) {
        if (name.length() > 10) {
            return ResponseEntity.badRequest().body(name + ": too long");
        }
        return ResponseEntity.ok(name + ": emm, a dog");
    }

}
