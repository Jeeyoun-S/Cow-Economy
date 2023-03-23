package com.coweconomy.common.jwt;

import com.coweconomy.domain.user.entity.User;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

// token 생성, 유효성 검사, 복호화 등 토큰에 관련된 함수 작성
@Slf4j
@Component
public class JwtTokenProvider {

//    public static final Key JWT_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${jwt.secret1}")
    private String secretKey1;

    // 토큰 유효시간
    private static final int JWT_EXPIRATION_MS = 604800000;

    // Jwt 토큰에서 아이디 추출
    public String getUserIdFromJWT(String token) {
        log.info("#4");
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey1.getBytes())
                .parseClaimsJws(token)
                .getBody();
        log.info("#5");

        log.info("id:"+claims.getId());
        log.info("issuer:"+claims.getIssuer());
        log.info("issue:"+claims.getIssuedAt().toString());
        log.info("subject:"+claims.getSubject());
        log.info("Audience:"+claims.getAudience());
        log.info("expire:"+claims.getExpiration().toString());
        log.info("userName:"+claims.get("userName"));

        return claims.getSubject();
    }

    // Jwt 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            System.out.println("hi");
            Jwts.parser().setSigningKey(secretKey1.getBytes()).parseClaimsJws(token);
            System.out.println("bye");
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature", e);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty.", e);
        }
        return false;
    }
}
