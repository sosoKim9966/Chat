package com.soso.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .headers()
                .frameOptions().sameOrigin()// SockJS는 기본적으로 HTML iframe 요소를 통한 전송을 허용하지 않도록 설정되는데 해당 내용을 해제한다.
            .and()
                .formLogin()
            .and()
                .authorizeRequests()
                    .antMatchers("/chat/**").hasRole("USER")
                    .anyRequest().permitAll();
    }

    /**
     * 테스트를 위해 In-Memory에 계정을 임의로 생성한다.
     * 서비스에 사용시에는 DB데이터를 이용하도록 수정이 필요하다.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                    .withUser("sosoKim")
                    .password("{noop}1234")
                    .roles("USER")
                .and()
                    .withUser("kkyu")
                    .password("{noop}1234")
                    .roles("USER")
                .and()
                    .withUser("guest")
                    .password("{noop}1234")
                    .roles("GUEST");

    }
}
