package com.omid.auth_service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;


@Component
@ConfigurationProperties(prefix = "app.security")
@Getter
@Setter
public class JwtHandler {

    private String issuer;
    private long expire;
//    private String privateKeyPath;
//    private String publicKeyPath;

    @Autowired
    private RSAPrivateKey privateKey;

    @Autowired
    private RSAPublicKey publicKey;

//    @PostConstruct
//    public void initKeys() throws Exception {
//        // بارگذاری private key
//        try (InputStream is = new ClassPathResource(privateKeyPath).getInputStream()) {
//            KeyFactory kf = KeyFactory.getInstance("RSA");
//            String key = new String(is.readAllBytes());
//            key = key.replaceAll("-----\\w+ PRIVATE KEY-----", "").replaceAll("\\s", "");
//            byte[] decoded = java.util.Base64.getDecoder().decode(key);
//            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded); // <-- درست
//            this.privateKey = (RSAPrivateKey) kf.generatePrivate(spec); // <-- درست
//        }
//
//        // بارگذاری public key
//        try (InputStream is = new ClassPathResource(publicKeyPath).getInputStream()) {
//            KeyFactory kf = KeyFactory.getInstance("RSA");
//            String key = new String(is.readAllBytes());
//            key = key.replaceAll("-----\\w+ PUBLIC KEY-----", "").replaceAll("\\s", "");
//            byte[] decoded = java.util.Base64.getDecoder().decode(key);
//            X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded); // درست
//            this.publicKey = (RSAPublicKey) kf.generatePublic(spec);
//        }
//    }

    public String generateToken(String username, String[] authorities) {
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(username)
                .withArrayClaim("authorities", authorities)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + expire))
                .sign(Algorithm.RSA256(null, privateKey));
    }


//    public DecodedJWT verifyToken(String token) {
//        return JWT.require(Algorithm.HMAC256(secretKey)).withIssuer(issuer).build().verify(token);
//    }

    public DecodedJWT verifyToken(String token) {
        return JWT.require(Algorithm.RSA256(publicKey, null))
                .withIssuer(issuer)
                .build()
                .verify(token);
    }
}
