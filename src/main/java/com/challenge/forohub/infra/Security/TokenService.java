package com.challenge.forohub.infra.Security;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.challenge.forohub.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security}")
    private String apiSecret;

    public String generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("foro_hub")
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("No se creó el token debido a un error");
        }
    }

    public String getSubject(String token) {
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("foro_hub")
                    .build()
                    .verify(token);
            verifier.getSubject();
        } catch (JWTVerificationException e) {
            System.out.println(e.getMessage());
        }
        if (verifier.getSubject() == null) {
            throw new RuntimeException("Verificación invalida");
        }
        return verifier.getSubject();
    }

    private Instant generarExpiracion() {
        return LocalDateTime.now().
                plusHours(5).toInstant(ZoneOffset.of("-03:00"));
    }

}
