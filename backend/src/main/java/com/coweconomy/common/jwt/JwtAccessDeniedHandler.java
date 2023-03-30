package com.coweconomy.common.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * 필요한 권한이 존재하지 않는 경우 403 Forbidden 에러를 반환
     * @param httpServletRequest that resulted in an <code>AccessDeniedException</code>
     * @param httpServletResponse so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

}
