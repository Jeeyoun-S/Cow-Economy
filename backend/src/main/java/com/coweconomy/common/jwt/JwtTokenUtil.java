package com.coweconomy.common.jwt;

import com.nimbusds.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtTokenUtil {
    private static String secretKey1;
    private static String secretKey2;
    private static Integer expirationTime;
    private static Integer expirationRefreshTime;

    @Autowired
    public JwtTokenUtil(@Value("${jwt.secret1}") String secretKey1, @Value("${jwt.secret2}") String secretKey2, @Value("${jwt.expiration}") Integer expirationTime, @Value("${jwt.expirationRefresh}") Integer expirationRefreshTime) {
        this.secretKey1 = secretKey1;
        this.secretKey2 = secretKey2;
        this.expirationTime = expirationTime;
        this.expirationRefreshTime = expirationRefreshTime;
    }

    Authentication authentication = new UserAuthentication(email, null, null);
    String token = JwtTokenProvider.generateToken(authentication);
        System.out.println("#123# : " + token);

    public static String getAccessToken(String email) {
        Date expires = JwtTokenUtil.getTokenExpiration(expirationTime);
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(expires)
                .withIssuer(ISSUER)
                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .sign(Algorithm.HMAC512(secretKey1.getBytes()));
    }
    public static Date getTokenExpiration(int expirationTime) {
        Date now = new Date();
        return new Date(now.getTime() + expirationTime);
    }
}
