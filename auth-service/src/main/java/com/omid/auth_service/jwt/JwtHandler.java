package com.omid.auth_service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@ConfigurationProperties(prefix = "app.security")
@Getter
@Setter
public class JwtHandler {
//    @Value("${app.security.issuer}")
    private String issuer;

//    @Value("${app.security.expire}")
    private long expire;

//    @Value("${app.security.secret-key}")
    private String secretKey;

    public String generateToken(String username, String[] authorities) {
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(username)
                .withArrayClaim("authorities", authorities)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + expire))
                .sign(Algorithm.HMAC256(secretKey));
    }

    public DecodedJWT verifyToken(String token) {
        return JWT.require(Algorithm.HMAC256(secretKey)).withIssuer(issuer).build().verify(token);
    }
}
