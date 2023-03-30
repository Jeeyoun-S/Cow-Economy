package com.coweconomy.common.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// TokenProvider를 주입받아 JwtFilter를 통해 security 로직에 등록하는 역할
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private JwtTokenUtil jwtTokenUtil;

    public JwtSecurityConfig(JwtTokenUtil jwtTokenProvider) {
        this.jwtTokenUtil = jwtTokenProvider;
    }
    /**
     * JwtAuthenticationFilter를 Security 로직에 필터로 등록
     * @param http
     */
    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenUtil), UsernamePasswordAuthenticationFilter.class);
    }
}
