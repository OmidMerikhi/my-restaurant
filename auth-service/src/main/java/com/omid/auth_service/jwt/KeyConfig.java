package com.omid.auth_service.jwt;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.converter.RsaKeyConverters;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@RequiredArgsConstructor
public class KeyConfig {
    private final Path privateKeyPath = Paths.get("D:\\projects\\keys\\my-restaurant\\private_key.pem");
    private final Path publicKeyPath = Paths.get("D:\\projects\\keys\\my-restaurant\\public_key.pem");



    @Bean
    RSAPublicKey publicKey() throws Exception {
        try (InputStream is = Files.newInputStream(publicKeyPath)) {
            return RsaKeyConverters.x509().convert(is);
        }
    }

    @Bean
    RSAPrivateKey privateKey() throws Exception {
        try (InputStream is = Files.newInputStream(privateKeyPath)) {
            return RsaKeyConverters.pkcs8().convert(is);
        }
    }



//    @Bean
//    public RSAKey rsaKey() throws Exception {
//        return new RSAKey.Builder(publicKey())
//                .privateKey(privateKey())
//                .keyID("my-name-is-omid")
//                .build();
//    }
//
//    @Bean
//    public JWKSet jwkSet(RSAKey rsaKey) {
//        return new JWKSet(rsaKey);
//    }


}
