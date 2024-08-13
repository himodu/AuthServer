package com.example.demo.domain.oauth.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Component
public class OAuthJwtUtil {

    private final String BAERER_PREFIX = "Bearer ";

    private final String USER_NAME = "username";
    private final String NAME = "name";
    private final String ROLE = "role";

    private final SecretKey SECRET_KEY;


    public OAuthJwtUtil(@Value("${jwt.secret}") String secret) {
        SECRET_KEY = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }


    private String unpackBearer(String token){
        return new String(token.substring(BAERER_PREFIX.length()));
    }

    public String getUsername(String token) {
        String unpackedToken = unpackBearer(token);
        return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(unpackedToken).getPayload().get(USER_NAME, String.class);
    }

    public String getName(String token) {
        String unpackedToken = unpackBearer(token);
        return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(unpackedToken).getPayload().get(NAME, String.class);
    }

    public String getRole(String token) {
        String unpackedToken = unpackBearer(token);
        return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(unpackedToken).getPayload().get(ROLE, String.class);
    }

    public Boolean isExpired(String token) {
        String unpackedToken = unpackBearer(token);
        return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(unpackedToken).getPayload().getExpiration().before(new Date());
    }

    public String createJwt(String username,String name, String role, Long expiredMs) {

        String token =  Jwts.builder()
                .claim(USER_NAME, username)
                .claim(ROLE, role)
                .claim(NAME, name)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SECRET_KEY)
                .compact();

        return BAERER_PREFIX+token;
    }
}
