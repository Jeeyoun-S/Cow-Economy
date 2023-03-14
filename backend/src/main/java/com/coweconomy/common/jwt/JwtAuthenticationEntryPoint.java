//package com.coweconomy.common.jwt;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import javax.naming.AuthenticationException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Slf4j
//@Component
//public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
//    /**
//     * 유효한 자격증명을 하지 않고 접근하려 할때 401.
//     *
//     * @param request
//     * @param response
//     * @param e
//     * @throws IOException
//     */
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//    }
//}
