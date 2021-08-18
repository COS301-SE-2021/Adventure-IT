//package com.adventureit.maincontroller.Security;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
//
//@Configuration
//class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests(auth -> auth
//                .antMatchers(HttpMethod.GET, "/**").authenticated())
//            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
//    }
//}
