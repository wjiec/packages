package com.wjiec.springaio.cloud.consumer;

import com.wjiec.springaio.cloud.consumer.feign.ItemService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Configuration
@EnableFeignClients
@SpringBootApplication
public class ConsumerApplication implements CommandLineRunner {

    private final RestTemplate restTemplate;
    private final WebClient.Builder webClientBuilder;
    private final ItemService itemService;

    public ConsumerApplication(@LoadBalanced RestTemplate restTemplate,
                               @LoadBalanced WebClient.Builder webClientBuilder,
                               ItemService itemService) {
        this.restTemplate = restTemplate;
        this.webClientBuilder = webClientBuilder;
        this.itemService = itemService;
        this.restTemplate.setInterceptors(new ArrayList<>() {{
            add((request, body, execution) -> {
                System.out.println(request.getURI());

                return execution.execute(request, body);
            });
        }});
    }

    @Bean
    @LoadBalanced
    public static RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @LoadBalanced
    public static WebClient.Builder webClient() {
        return WebClient.builder();
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        for (int i = 0; i < 10; i++) {
            doExchangeItemService("hello-" + i);
            doExchangeItemServiceFlux("world-" + i);

            System.out.println(itemService.item("awesome" + i));
        }
    }

    private void doExchangeItemService(String name) {
        var resp = restTemplate
            .exchange("http://item-service/item/{name}", HttpMethod.GET, null, String.class, name);
        System.out.println(resp.getStatusCode());
        System.out.println(resp.getHeaders());
        System.out.println(resp.getBody());
        System.out.println();
    }

    private void doExchangeItemServiceFlux(String name) {
        webClientBuilder.build()
            .get()
                .uri("http://item-service/item/{name}", name)
            .exchangeToMono(resp -> {
                System.out.println(resp.statusCode());
                System.out.println(resp.headers());

                return resp.bodyToMono(String.class);
            })
            .subscribe(System.out::println);
    }

}
