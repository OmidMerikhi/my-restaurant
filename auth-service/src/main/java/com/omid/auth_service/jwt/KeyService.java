package com.omid.auth_service.jwt;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class KeyService {
    private final Map<String, RSAKey> keys = new ConcurrentHashMap<>();
    private volatile String activeKid;
    private final KeyConfig keyConfig;

    @PostConstruct
    public void init() throws Exception {
        rotate();
    }

    public synchronized void rotate() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();

        String kid = "kid-" + System.currentTimeMillis();

        RSAKey rsaKey = new RSAKey.Builder((keyConfig.publicKey()))
                .privateKey(keyConfig.privateKey())
                .keyID(kid)
                .build();

        keys.put(kid, rsaKey);
        activeKid = kid;
    }

    @Bean
    public RSAKey activeKey() {
        return keys.get(activeKid);
    }

    @Bean
    public JWKSet jwkSet() {
        return new JWKSet(new ArrayList<>(keys.values()));
    }


//    private final Path privateKeyPath = Paths.get("D:\\projects\\keys\\my-restaurant\\private_key.pem");
//    private final Path publicKeyPath = Paths.get("D:\\projects\\keys\\my-restaurant\\public_key.pem");

//    public void rotateKeys() throws IOException, InterruptedException {
//        // 1️⃣ تولید کلید خصوصی جدید
//        ProcessBuilder genPrivate = new ProcessBuilder(
//                "openssl", "genpkey",
//                "-algorithm", "RSA",
//                "-out", privateKeyPath.toString(),
//                "-pkeyopt", "rsa_keygen_bits:2048"
//        );
//        Process process1 = genPrivate.inheritIO().start();
//        int exitCode1 = process1.waitFor();
//        if (exitCode1 != 0) {
//            throw new RuntimeException("Failed to generate private key");
//        }

//        // 2️⃣ استخراج کلید عمومی جدید
//        ProcessBuilder genPublic = new ProcessBuilder(
//                "openssl", "rsa",
//                "-pubout",
//                "-in", privateKeyPath.toString(),
//                "-out", publicKeyPath.toString()
//        );
//        Process process2 = genPublic.inheritIO().start();
//        int exitCode2 = process2.waitFor();
//        if (exitCode2 != 0) {
//            throw new RuntimeException("Failed to generate public key");
//        }
//    }
}
