package com.coweconomy.common.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /**    모든 메소드 요청에서 실행됨
    *      요청의 URI를 로깅하고 토큰 검증 및 인증 수행
    */
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        log.info(request.getRequestURI());
        if((!request.getRequestURI().contains("my-page") || !request.getRequestURI().contains("login")) && !request.getRequestURI().contains("favicon")) {
            log.info("토큰 체크");
            try {
                System.out.println("##!!@@##");
                String jwt = getJwtFromRequest(request); //request에서 jwt 토큰을 꺼낸다.
                log.info(jwt);
                if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                    String userId = jwtTokenProvider.getUserIdFromJWT(jwt); //jwt에서 사용자 id를 꺼낸다.
                    log.info("userId : " + userId);
                    log.info("#3");

                    UserAuthentication authentication = new UserAuthentication(userId, null, null); //id를 인증한다.
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //기본적으로 제공한 details 세팅
                    log.info("#1");
                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication); //세션에서 계속 사용하기 위해 securityContext에 Authentication 등록
                    log.info("#2");
                } else {
                    if (StringUtils.isEmpty(jwt)) {
                        request.setAttribute("unauthorization", "401 인증키 없음.");
                    }

                    if (jwtTokenProvider.validateToken(jwt)) {
                        request.setAttribute("unauthorization", "401-001 인증키 만료.");
                    }
                }
            } catch (Exception ex) {
                logger.error("Could not set user authentication in security context", ex);
                ex.printStackTrace(); // Add this line to print the exception stack trace
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        log.info("bearerToken : " + bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            log.info("Bearer exist");
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }
}