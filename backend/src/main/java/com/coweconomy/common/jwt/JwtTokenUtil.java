package com.coweconomy.common.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenUtil {
    public static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final String AUTHORITIES_KEY = "auth";
    private static String secretKey1;
    private static String secretKey2;
    private static Integer expirationTime;
    private static Integer expirationRefreshTime;

    @Autowired
    public JwtTokenUtil(
            @Value("${jwt.secret1}") String secretKey1,
            @Value("${jwt.secret2}") String secretKey2,
            @Value("${jwt.expiration}") Integer expirationTime,
            @Value("${jwt.expirationRefresh}") Integer expirationRefreshTime)
    {
        this.secretKey1 = secretKey1;
        this.secretKey2 = secretKey2;
        this.expirationTime = expirationTime;
        this.expirationRefreshTime = expirationRefreshTime;
    }

    /**
     * userEmail과 userId를 포함한 AccessToken 발급
     * @param userEmail
     * @param userId
     * @return
     */
    public static String getAccessToken(String userEmail, Long userId) {
        // 토큰 만료 시간
        Date expires = JwtTokenUtil.getTokenExpiration(expirationTime);

        return Jwts.builder()
                .setSubject(userEmail)                          // 사용자
                .setIssuedAt(new Date())                        // 현재 시간 기반 생성
                .setExpiration(expires)                         // 만기 시간
                .claim("userId", userId)
                .claim("userEmail", userEmail)
                .signWith(SignatureAlgorithm.HS512, secretKey1) // 사용할 암호화 알고리즘
                .compact();
    }

    /**
     * userEmail과 userId를 포함한 RefreshToken 발급
     * @param userEmail
     * @param userId
     * @return
     */
    public static String getRefreshToken(String userEmail, Long userId) {
        Date expires = JwtTokenUtil.getTokenExpiration(expirationRefreshTime);

        return Jwts.builder()
                .setSubject(userEmail)                          // 사용자
                .setIssuedAt(new Date())                        // 현재 시간 기반 생성
                .setExpiration(expires)                         // 만기 시간
                .claim("userId", userId)
                .claim("userEmail", userEmail)
                .signWith(SignatureAlgorithm.HS512, secretKey2) // 사용할 암호화 알고리즘
                .compact();
    }

    /**
     * 토큰 만료시간 계산
     * @param expirationTime
     * @return
     */
    public static Date getTokenExpiration(int expirationTime) {
        Date now = new Date();
        return new Date(now.getTime() + expirationTime);
    }

    /**
     * Token에 담겨있는 정보(권한)를 사용하여 user 객체 반환
     * @param token
     * @return UsernamePasswordAuthenticationToken
     */
    public Authentication getAuthentication(String token) {
        // accessToken을 사용하여 claims 생성
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(secretKey1)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String userEmail = claims.get("userEmail", String.class);
        Long userId = claims.get("userId", Long.class);

        // DB를 거치지 않고 token에서 값(권한 정보)을 사용하여 user 객체 생성
        UserDetails principal = User.withUsername(userEmail)
                .password("")
                .authorities(Collections.emptyList())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();

        return new UsernamePasswordAuthenticationToken(principal, token, new ArrayList<>());
    }

    /**
     * accessToken 만료시 refreshToken을 확인해 재발급
     * 구현 시 수정 필요
     * @param refreshToken
     * @return
     */
    public String refreshToken(String refreshToken) {
        if (!validateRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }
        Claims claims = getClaims(refreshToken, secretKey2);
        Long userId = claims.get("userId", Long.class);
        String userEmail = claims.get("userEmail", String.class);
        return getAccessToken(userEmail, userId);
    }

    /**
     * RefreshToken 검증
     * 구현 시 수정 필요
     * @param token
     * @return
     */
    public boolean validateRefreshToken(String token) {
        System.out.println(secretKey2);
        try {
            Jwts
                    .parserBuilder()
                    .setSigningKey(secretKey2)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }

        return false;
    }

    private Claims getClaims(String token, String secretKey) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    /**
     * Access Token의 유효성 검증 수행
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jwts
                    .parserBuilder()
                    .setSigningKey(secretKey1)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public String getUserEmailFromToken(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(secretKey1)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
