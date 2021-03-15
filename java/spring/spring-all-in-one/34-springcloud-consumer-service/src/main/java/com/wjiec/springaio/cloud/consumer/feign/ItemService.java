package com.wjiec.springaio.cloud.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "item-service")
public interface ItemService {

    @GetMapping("/item/{name}")
    String item(@PathVariable String name);

}
