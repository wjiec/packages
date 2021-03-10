package com.wjiec.springaio.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class WebFluxSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .authorizeExchange()
                .pathMatchers("/bye").hasRole("ADMIN")
                .anyExchange().permitAll()
            .and()
            .csrf()
                .disable()
            .httpBasic()
            .and()
            .build();
    }

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService() {
        return new MapReactiveUserDetailsService(
            User.builder().username("admin").password("{noop}admin").roles("ADMIN").build());
    }

}
