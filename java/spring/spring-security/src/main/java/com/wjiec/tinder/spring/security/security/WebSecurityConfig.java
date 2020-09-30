package com.wjiec.tinder.spring.security.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disabled X-Frame-Options: deny
        http.regexMatcher("/h2-console/.*").headers().frameOptions().sameOrigin();

        // Disabled CSRF check
        http.csrf().disable();

        // Disabled CORS check
        http.cors().disable();

        // Permit all request
        http.authorizeRequests()
            .anyRequest().permitAll();
    }

}
