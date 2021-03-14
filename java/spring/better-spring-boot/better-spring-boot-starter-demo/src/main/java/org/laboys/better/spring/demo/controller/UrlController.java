package org.laboys.better.spring.demo.controller;

import org.laboys.better.spring.demo.dto.url.ShortenParam;
import org.laboys.better.spring.demo.dto.url.ShortenVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/url")
public class UrlController {

    @PostMapping("/shorten")
    public ShortenVO shorten(@RequestBody @Validated ShortenParam param) {
        return ShortenVO.builder()
            .logUrl(param.getLogUrl())
            .shortUrl(param.getLogUrl())
            .build();
    }

}
