package com.example.apigateway.utils;

import com.example.apigateway.exception.JwtTokenMalformedException;
import com.example.apigateway.exception.JwtTokenMissingException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
@Slf4j
public class JwtUtil {

    private static final String AUTHORITIES_KEY = "auth";
    private final Key key;

    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public boolean validateToken(String token) throws JwtTokenMalformedException, JwtTokenMissingException {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("wrong JWT sign");
        } catch (ExpiredJwtException e) {
            log.info("expired JWT");
        } catch (UnsupportedJwtException e) {
            log.info("unsupported jwt token");
        } catch (IllegalArgumentException e) {
            log.info("Illegal JWT token");
        }
        return false;
    }

}
