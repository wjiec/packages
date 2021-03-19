package com.wjiec.springaio.cloud.hystrix.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "item-service", path = "/item")
public interface ItemService {

    @GetMapping("/{name}")
    String item(@PathVariable String name);

}
