package com.coweconomy.common.jwt;

// JWT를 위한 커스텀 필터
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
     * @param servletRequest  The request to process
     * @param servletResponse The response associated with the request
     * @param filterChain    Provides access to the next filter in the chain for this
     *                 filter to pass the request and response to for further
     *                 processing
     *
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        // request에서 token 정보 받기
        String accessToken = resolveToken(httpServletRequest, AUTHORIZATION_HEADER);
        String refreshToken = resolveToken(httpServletRequest, REFRESH_HEADER);

        // accessToken 만료 시 refreshToken 검증 후 재발급
        // 구현 시 코드 수정
        if (jwtTokenUtil.validateToken(accessToken)) {
            // success) 정상 토큰이라면 SecurityContext에 저장
            Authentication authentication = jwtTokenUtil.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            if (jwtTokenUtil.validateRefreshToken(refreshToken)) {
                String newAccessToken = jwtTokenUtil.refreshToken(refreshToken);
                httpServletResponse.setHeader("New-Access-Token", "Bearer " + newAccessToken);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 헤더의 토큰 가져오기
     * refresh 구현 시 수정 필요
     * @param httpServletRequest
     * @param headerName
     * @return
     */
    private String resolveToken(HttpServletRequest httpServletRequest, String headerName) {
        String bearerToken = httpServletRequest.getHeader(headerName);

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            return token;
        } else {
            logger.info("토큰 없음");
        }
        return null;
    }
}