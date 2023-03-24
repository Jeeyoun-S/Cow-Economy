package com.coweconomy.common.jwt;

import com.nimbusds.jwt.JWT;
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

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    @Autowired
    public JwtTokenUtil(@Value("${jwt.secret1}") String secretKey1, @Value("${jwt.secret2}") String secretKey2, @Value("${jwt.expiration}") Integer expirationTime, @Value("${jwt.expirationRefresh}") Integer expirationRefreshTime) {
        this.secretKey1 = secretKey1;
        this.secretKey2 = secretKey2;
        this.expirationTime = expirationTime;
        this.expirationRefreshTime = expirationRefreshTime;
    }

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
    /**
     * Token에 담겨있는 정보(권한)를 사용하여 Authentication 객체 반환
     * @param String
     * @return UsernamePasswordAuthenticationToken
     */
    public Authentication getAuthentication(String token) {
        // i) token을 사용하여 claims 생성
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(secretKey1)
                .build()
                .parseClaimsJws(token)
                .getBody();

//        // ii) claims에 있는 권한 추출
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        String userEmail = claims.get("userEmail", String.class);
        Long userId = claims.get("userId", Long.class);

        // iii) DB를 거치지 않고 token에서 값(권한 정보)을 사용하여 user 객체 생성
//        org.springframework.security.core.userdetails.User principal = new User(claims.getSubject(), "", new ArrayList<>());
        UserDetails principal = User.withUsername(userEmail)
                .password("")
                .authorities(Collections.emptyList())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();

        // iv) user 객체, 토큰, 권한정보를 사용하여 최종적으로 Authentication(인증) 객체를 반환
        return new UsernamePasswordAuthenticationToken(principal, token, new ArrayList<>());
    }
    /**
     * Token의 유효성 검증 수행
     * @param String
     * @return boolean
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey1).build().parseClaimsJws(token);
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
}
