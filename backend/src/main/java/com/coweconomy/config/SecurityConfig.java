package com.coweconomy.config;

//import com.coweconomy.common.jwt.JwtAuthenticationEntryPoint;
//import com.coweconomy.common.jwt.JwtAuthenticationFilter;
import java.util.Arrays;
import java.util.Collections;

import com.coweconomy.common.jwt.JwtAccessDeniedHandler;
import com.coweconomy.common.jwt.JwtAuthenticationEntryPoint;
import com.coweconomy.common.jwt.JwtAuthenticationFilter;
import com.coweconomy.common.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 교차출처 리소스 공유(CORS) 설정
                .cors()
                .and()

                // CSRF(Cross Site Request Forgery) 사이트 간 요청 위조 설정
                .csrf()
                .disable()

                // 인증, 허가 에러 시 공통적으로 처리해주는 부분
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()

                .sessionManagement() //(4)
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                // UsernamePasswordAuthenticationFilter보다 JwtAuthenticationFilter를 먼저 수행
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenUtil), UsernamePasswordAuthenticationFilter.class)
//                 HttpServeltRequest를 사용하는 요청들에 접근 제한 설정
                .authorizeRequests()
//                .antMatchers("/api/**")
//                .permitAll()		// 모두 허용

                // 로그인할 때는 검증 X
//                .antMatchers("/auth/login/**")
//                .antMatchers("/auth/**")
                .antMatchers("/**")
                .permitAll()

//                 나머지는 전부 인증 필요
                .anyRequest()
                .authenticated()

                // 시큐리티는 기본적으로 세션을 사용
                // 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
        ;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    //비밀번호 암호화를 위한 Encoder 설정
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}