package com.review.crud.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "MySecretKeyIsSuperKeyUsedWithHeaderAndPayload";
    private final long Access_EXPIRATION = 1000 * 60 * 60;
    private final long Refresh_EXPIRATION = 1000 * 60 * 60 * 24 * 7;

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());


    public String generateAccessToken(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .setIssuer("crud")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Access_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .setIssuer("crud")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Refresh_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    public boolean validateToken(String token, String userName) {
        return userName.equals(extractUsername(token)) && !isExpired(token);
    }

    private boolean isExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiration.before(new Date());
    }
}