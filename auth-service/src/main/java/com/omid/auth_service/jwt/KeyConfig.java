package com.omid.auth_service.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.converter.RsaKeyConverters;

import java.io.InputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
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


}
