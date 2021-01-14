package com.wjiec.springaio.shop.config;

import com.wjiec.springaio.shop.domain.User;
import com.wjiec.springaio.shop.repository.UserRepository;
import com.wjiec.springaio.shop.type.Session;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailService userDetailService;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(UserDetailService userDetailService, Md5PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/register", "/login", "/css/**")
                    .permitAll()
                .anyRequest()
                    .authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/session")
                .usernameParameter("username")
                .passwordParameter("passcode")
                .successForwardUrl("/")
            .and()
            .logout()
                .logoutUrl("/drop")
                .logoutSuccessUrl("/")
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("jayson").password("{noop}jayson").roles("USER")
                .and()
                .withUser("admin").password("{noop}admin").roles("USER", "ADMIN")
                .and()
            .and()
            .userDetailsService(userDetailService)
            .passwordEncoder(passwordEncoder);
    }

    @Service
    public static class UserDetailService implements UserDetailsService {

        private final UserRepository userRepository;

        public UserDetailService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByUsername(username);
            if (user != null) {
                return new Session(user);
            }

            throw new UsernameNotFoundException("the username (" + username + ") not found");
        }
    }

    @Component
    public static class Md5PasswordEncoder implements PasswordEncoder {
        @Override
        public String encode(CharSequence rawPassword) {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");

                md5.update(rawPassword.toString().getBytes());
                return new String(new BigInteger(md5.digest()).toByteArray());
            } catch (NoSuchAlgorithmException e) {
                return rawPassword.toString();
            }
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return encode(rawPassword).equals(encodedPassword);
        }
    }

}
