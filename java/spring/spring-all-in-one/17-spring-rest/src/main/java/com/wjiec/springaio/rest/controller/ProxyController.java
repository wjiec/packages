package com.wjiec.springaio.rest.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
public class ProxyController {

    @GetMapping("/proxy/get")
    public Object proxy(@RequestParam String url, RestTemplate client) {
        if (url != null && !url.isBlank()) {
            GetResponse resp = client.getForObject("https://httpbin.org/get?url={url}", GetResponse.class, url);
            if (resp != null) {
                return resp;
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("bad url");
        }

        return ResponseEntity.badRequest().body("unknown url");
    }

    @GetMapping("/proxy-metadata/get")
    public Object proxyMetadata(@RequestParam String url, RestTemplate client) {
        if (url != null && !url.isBlank()) {
            ResponseEntity<GetResponse> resp = client.getForEntity("https://httpbin.org/get?url={url}", GetResponse.class, url);
            if (resp.getStatusCode().is2xxSuccessful()) {
                return List.of(resp.getBody(), resp.getHeaders());
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("bad url");
        }

        return ResponseEntity.badRequest().body("unknown url");
    }

    @GetMapping("/proxy-exchange/post")
    public Object proxyExchange(@RequestParam String url, RestTemplate client) {
        if (url != null && !url.isBlank()) {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("X-Authorization", UUID.randomUUID().toString());
            HttpEntity<?> request = new HttpEntity<>(headers);

            ResponseEntity<GetResponse> resp = client.exchange("https://httpbin.org/post?url={url}", HttpMethod.POST, request, GetResponse.class, url);
            if (resp.getStatusCode().is2xxSuccessful()) {
                return List.of(resp.getBody(), resp.getHeaders());
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("bad url");
        }

        return ResponseEntity.badRequest().body("unknown url");
    }

    @Data
    public static class GetResponse {
        private Map<String, String> args;
        private Map<String, String> headers;
        private String origin;
        private String url;
    }

}
