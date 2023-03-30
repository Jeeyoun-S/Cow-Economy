package com.coweconomy.common.jwt;

// JWT를 위한 커스텀 필터
import com.coweconomy.common.jwt.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    public static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "Refresh";
    private JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * Token의 인증 정보를 SecurityContext에 저장
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        // i) request에서 token 정보 받기
        String accessToken = resolveToken(httpServletRequest, AUTHORIZATION_HEADER);
        String refreshToken = resolveToken(httpServletRequest, REFRESH_HEADER);

        String requestURI = httpServletRequest.getRequestURI();


        // ii) token이 유효할 때
//        if (StringUtils.hasText(token) && jwtTokenUtil.validateToken(token)) {
//        try {
            if (jwtTokenUtil.validateToken(accessToken)) {
                // success) 정상 토큰이라면 SecurityContext에 저장
                Authentication authentication = jwtTokenUtil.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
//            else {
//                logger.info("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
//            }
         else {
            System.out.println("#465");
            if (jwtTokenUtil.validateRefreshToken(refreshToken)) {
                String newAccessToken = jwtTokenUtil.refreshToken(refreshToken);
                httpServletResponse.setHeader("New-Access-Token", "Bearer " + newAccessToken);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(HttpServletRequest httpServletRequest, String headerName) {
        String bearerToken = httpServletRequest.getHeader(headerName);

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            System.out.println("여기가 어디게? " + bearerToken.substring(7));
            String token = bearerToken.substring(7);
            logger.info("Token found {}", token);
            return token;
        } else {
            logger.info("토큰 없음");
        }
        return null;
    }
}