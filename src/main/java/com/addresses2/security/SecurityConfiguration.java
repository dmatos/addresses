package com.addresses2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeRequests(auths ->
                        auths
                                .antMatchers(HttpMethod.GET, "/addresses/*").permitAll()
                                .antMatchers(HttpMethod.POST, "/addresses/*").permitAll()
                                .anyRequest().permitAll()
                )
                .cors().and().csrf().disable();
        return http.build();
    }
}
