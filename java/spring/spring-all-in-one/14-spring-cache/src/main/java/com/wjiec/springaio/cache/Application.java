package com.wjiec.springaio.cache;

import com.wjiec.springaio.cache.service.TimeService;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.List;

@ComponentScan
@Configuration
@EnableCaching
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Application.class);
        context.refresh();

        TimeService timeService = context.getBean(TimeService.class);
        System.out.println(timeService.now());
        System.out.println(timeService.now());
        System.out.println(timeService.now());

        System.out.println(timeService.update());
        System.out.println(timeService.now());
        System.out.println(timeService.now());

        timeService.remove();
        System.out.println(timeService.update());
        System.out.println(timeService.now());
    }

    @Bean
    @Primary
    public CompositeCacheManager compositeCacheManager() {
        CompositeCacheManager cacheManager = new CompositeCacheManager();
        cacheManager.setCacheManagers(List.of(
            concurrentMapCacheManager()
//            redisCacheManager()
        ));

        return cacheManager;
    }

    @Bean
    public ConcurrentMapCacheManager concurrentMapCacheManager() {
        return new ConcurrentMapCacheManager();
    }

//    @Bean
//    public EhCacheCacheManager ehCacheCacheManager(CacheManager cacheManager) {
//        return new EhCacheCacheManager(cacheManager);
//    }
//
//    @Bean
//    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
//        EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
//        factoryBean.setConfigLocation(new ClassPathResource("ehcahce.xml"));
//
//        return factoryBean;
//    }

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory();
//    }
//
//    @Bean
//    public RedisCacheManager redisCacheManager() {
//        return RedisCacheManager.create(redisConnectionFactory());
//    }

}
