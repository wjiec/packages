package com.wjiec.springboot.rbac.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    /**
     * 配置拦截器来保护请求
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
                .disable()
            .formLogin()
                .disable()
            .httpBasic()
                .realmName("")
            .and()
            .authorizeRequests()
                .antMatchers("/v*/account/login").permitAll()
                .anyRequest().authenticated();
    }

}
