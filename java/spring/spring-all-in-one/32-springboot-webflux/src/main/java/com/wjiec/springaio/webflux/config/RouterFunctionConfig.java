package com.wjiec.springaio.webflux.config;

import com.wjiec.springaio.webflux.model.Item;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

@Configuration
public class RouterFunctionConfig {

    @Bean
    public RouterFunction<?> helloWorld() {
        return RouterFunctions
            .route(RequestPredicates.GET("/hello"), request ->
                ServerResponse.ok().body(Mono.just("hello world"), String.class))
            .andRoute(RequestPredicates.GET("/bye"), request ->
                ServerResponse.ok().body(Mono.just("bye " + request.path()), String.class))
            .andRoute(RequestPredicates.GET("item/random"), this::randomItem);
    }

    public Mono<ServerResponse> randomItem(ServerRequest request) {
        return ServerResponse.ok()
            .body(Mono.just(Item.builder()
                .title(request.queryParam("title").orElse("random"))
                .build()), Item.class);
    }

}
