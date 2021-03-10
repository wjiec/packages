package com.wjiec.springaio.webflux;

import com.wjiec.springaio.webflux.model.Item;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@SpringBootApplication
public class WebFluxApplication implements CommandLineRunner {

    private final WebClient webClient;

    public WebFluxApplication() {
        this.webClient = WebClient.create("http://localhost:8080");
    }

    public static void main(String[] args) {
        SpringApplication.run(WebFluxApplication.class, args);
    }

    @Override
    public void run(String... args) {
        webClient.get().uri("/item")
            .retrieve()
            .bodyToFlux(Item.class)
            // http ok
            .timeout(Duration.ofSeconds(1))
            .buffer()
            .subscribe((v) -> System.out.printf("get /item : %s\n", v));

        Item item = Item.builder().title("webClient").build();
        webClient.post().uri("/item")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(item), Item.class)
            .retrieve()
            .bodyToMono(Item.class)
            // http ok
            .subscribe((v) -> System.out.printf("post /item : %s\n", v));

        webClient.delete().uri("/item")
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, response -> {
                System.out.printf("delete /item resp : %s", response);

                return Mono.just(new Exception(response.headers().toString()));
            })
            .bodyToMono(Void.class)
            .subscribe(System.out::println, error -> System.out.printf("delete /item: %s\n", error.getMessage()));

        webClient.get().uri("/hello")
            .exchangeToMono(response -> {
                System.out.printf("get /hello cookie : %s\n", response.cookies());
                System.out.printf("get /hello header : %s\n", response.headers());

                return response.bodyToMono(String.class);
            })
            .subscribe((v) -> System.out.printf("get /hello : %s\n", v));
    }

}
