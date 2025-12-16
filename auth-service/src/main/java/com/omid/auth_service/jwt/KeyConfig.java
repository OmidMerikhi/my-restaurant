package com.omid.auth_service.jwt;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.converter.RsaKeyConverters;

import java.io.InputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@RequiredArgsConstructor
public class KeyConfig {

    @Bean
    RSAPublicKey publicKey() throws Exception {
        try (InputStream inputStream = new ClassPathResource("keys/public_key.pem").getInputStream()) {
            return RsaKeyConverters.x509().convert(inputStream);
        }
    }

    @Bean
    RSAPrivateKey privateKey() throws Exception {
        try (InputStream inputStream = new ClassPathResource("keys/private_key.pem").getInputStream()) {
            return RsaKeyConverters.pkcs8().convert(inputStream);
        }
    }

    @Bean
    public RSAKey rsaKey() throws Exception {
        return new RSAKey.Builder(publicKey())
                .privateKey(privateKey())
                .keyID("my-name-is-omid")
                .build();
    }

    @Bean
    public JWKSet jwkSet(RSAKey rsaKey) {
        return new JWKSet(rsaKey);
    }


}
