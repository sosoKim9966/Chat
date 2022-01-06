package com.soso.chat.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("{spring.jwt.secret}")
    private String secretKey;

    private long tokenValidMillisecond  = 10000L * 60 * 60;

    /**
     * 이름으로 Jwt Token을 생성한다.
     */
    public String generateToken(String name) {
        Date now = new Date();
        return Jwts.builder()
                .setId(name)
                .setIssuedAt(now) // 토큰 발행 일자
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond)) // 유효시간 설정
                .signWith(SignatureAlgorithm.ES256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact();
    }

    /**
     * Jwt Token을 복호화 하여 이름을 얻는다.
     */
    public String getUserNameFormJwt(String jwt) {
        return getClaims(jwt).getBody().getId();
    }

    /**
     * Jwt Token의 유효성을 체크한다.
     */
    public boolean validateToken(String Jwt) {
        return this.getClaims(Jwt) != null;
    }

    /**
     * Jwt 유효성 검사
     */
    private Jws<Claims> getClaims(String jwt) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt);
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            throw ex;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT Token");
            throw ex;
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT Token");
            throw ex;
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT Token");
            throw ex;
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
            throw ex;
        }

    }


}
