package com.wjiec.springaio.security.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    private final DataSource dataSource;
//
//    @Autowired
//    public WebSecurityConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

//    @Autowired
//    private MongoTemplate mongoTemplate;

    /**
     * 配置SpringSecurity的Filter链
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     * 配置拦截器来保护请求
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }

    /**
     * 配置user-detail服务
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin").password("{noop}admin").roles("ADMIN", "USER")
            .and()
            .withUser("user").password("{noop}user").roles("USER");

//        auth.jdbcAuthentication()
//            .dataSource(dataSource)
//            // DEFAULT: select username,password,enabled from users where username = ?
//            .usersByUsernameQuery("")
//            // DEFAULT: select username,authority from authorities where username = ?
//            .authoritiesByUsernameQuery("")
//            // DEFAULT: select g.id, g.group_name, ga.authority from groups g, group_members gm, group_authorities ga
//            //          where gm.username = ? and g.id = ga.group_id and g.id = gm.group_id
//            .groupAuthoritiesByUsername("");

//        auth.ldapAuthentication()
//            .userSearchFilter("uid={0}")
//            .groupSearchFilter("member={0}")
//            .contextSource()
//                // embedded ldap
//                .root("dc=xxx,dc=yyy");

//        auth.userDetailsService(new MongoUserService(mongoTemplate));
    }

    private static class MongoUserService implements UserDetailsService {

        private final MongoTemplate mongoTemplate;

        public MongoUserService(MongoTemplate mongoTemplate) {
            this.mongoTemplate = mongoTemplate;
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            AdminUser adminUser = mongoTemplate.findOne(Query.query(Criteria.where("username").is(username)), AdminUser.class);

            return new User(adminUser.username, adminUser.password,
                List.of(new SimpleGrantedAuthority("ROLE_"+adminUser.role)));
        }

        @Data
        @Document
        static class AdminUser {
            private String username;
            private String password;
            private String role;
        }
    }

}
