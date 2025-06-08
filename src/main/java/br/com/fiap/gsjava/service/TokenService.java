package br.com.fiap.gsjava.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String genToken(UserDetails user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("Global Solution API")
                    .withSubject(user.getUsername())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new JWTCreationException("JWT Token: Generate token", e.getCause());
        }
    }

    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("Global Solution API")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("JWT Token: Invalid/Expired!");
        }
    }


    private Instant expirationDate() {
        return LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.of("-03:00"));
    }
}
