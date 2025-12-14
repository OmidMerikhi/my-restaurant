package com.omid.auth_service.jwt;

import com.nimbusds.jose.jwk.RSAKey;
import com.omid.auth_service.util.PemUtil;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class KeyManager {
    private final Path privateKeyPath = Paths.get("src/main/resources/keys/private_key.pem");
    private final Path publicKeyPath  = Paths.get("src/main/resources/keys/public_key.pem");

    @Getter
    private volatile RSAKey activeKey;
    private final Map<String, RSAKey> oldKeys = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() throws Exception {
        this.activeKey = loadKeyFromFiles("key-initial");
    }

    public RSAKey loadKeyFromFiles(String keyId) throws Exception {
        RSAPrivateKey privateKey = RsaKeyConverters.pkcs8()
                .convert(Files.newInputStream(privateKeyPath));

        RSAPublicKey publicKey = RsaKeyConverters.x509()
                .convert(Files.newInputStream(publicKeyPath));

        return new com.nimbusds.jose.jwk.RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(keyId)
                .build();
    }

    public synchronized void rotateKeys() throws Exception {

        // کلید قبلی را نگه می‌داریم
        oldKeys.put(activeKey.getKeyID(), activeKey);

        // تولید کلید جدید
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(2048);
        KeyPair pair = gen.generateKeyPair();

        // تبدیل به PEM و ذخیره در فایل
        String privatePem = PemUtil.toPrivatePem(pair.getPrivate());
        String publicPem  = PemUtil.toPublicPem(pair.getPublic());

        Files.writeString(privateKeyPath, privatePem);
        Files.writeString(publicKeyPath, publicPem);

        // کلید جدید را بارگذاری کنیم
        this.activeKey = loadKeyFromFiles(UUID.randomUUID().toString());
    }

    public Map<String, RSAKey> getAllKeys() {
        Map<String, RSAKey> map = new HashMap<>(oldKeys);
        map.put(activeKey.getKeyID(), activeKey);
        return map;
    }
}
