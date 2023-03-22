package com.coweconomy.common.jwt;

import com.coweconomy.domain.user.entity.User;
import com.nimbusds.jwt.JWT;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenUtil {
    private static String secretKey1;
    private static String secretKey2;
    private static Integer expirationTime;
    private static Integer expirationRefreshTime;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    @Autowired
    public JwtTokenUtil(@Value("${jwt.secret1}") String secretKey1, @Value("${jwt.secret2}") String secretKey2, @Value("${jwt.expiration}") Integer expirationTime, @Value("${jwt.expirationRefresh}") Integer expirationRefreshTime) {
        this.secretKey1 = secretKey1;
        this.secretKey2 = secretKey2;
        this.expirationTime = expirationTime;
        this.expirationRefreshTime = expirationRefreshTime;
    }

//    Authentication authentication = new UserAuthentication(email, null, null);
//    String token = JwtTokenProvider.generateToken(authentication);
//        System.out.println("#123# : " + token);
//    public static final Key JWT_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public static String getAccessToken(String userEmail, Long userId) {
        Date expires = JwtTokenUtil.getTokenExpiration(expirationTime);

        return Jwts.builder()
                .setSubject(userEmail) // 사용자
                .setIssuedAt(new Date())                            // 현재 시간 기반 생성
                .setExpiration(expires)
                .claim("userId", userId)
                .claim("userEmail", userEmail)
                .signWith(SignatureAlgorithm.HS512, secretKey1.getBytes())     // 사용할 암호화 알고리즘
                .compact();
    }

    public static String getRefreshToken(String userEmail, Long userId) {
        Date expires = JwtTokenUtil.getTokenExpiration(expirationRefreshTime);

        return Jwts.builder()
                .setSubject(userEmail)
                .setIssuedAt(new Date())
                .setExpiration(expires)
                .claim("userId", userId)
                .claim("userEmail", userEmail)
                .signWith(SignatureAlgorithm.HS512, secretKey2.getBytes())
                .compact();
    }
    public static Date getTokenExpiration(int expirationTime) {
        Date now = new Date();
        return new Date(now.getTime() + expirationTime);
    }

    // Jwt 토큰 유효성 검사
//    public static boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(secretKey2.getBytes()).parseClaimsJws(token);
//            return true;
//        } catch (SignatureException e) {
//            log.error("Invalid JWT signature", e);
//        } catch (MalformedJwtException e) {
//            log.error("Invalid JWT token", e);
//        } catch (ExpiredJwtException e) {
//            log.error("Expired JWT token", e);
//        } catch (UnsupportedJwtException e) {
//            log.error("Unsupported JWT token", e);
//        } catch (IllegalArgumentException e) {
//            log.error("JWT claims string is empty.", e);
//        }
//        return false;
//    }
}
