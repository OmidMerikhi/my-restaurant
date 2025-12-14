package com.omid.auth_service.jwt;

import com.nimbusds.jose.jwk.RSAKey;
import com.omid.auth_service.util.PemUtil;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
    @Value("${app.security.keys-dir}")
    private Path keysDir;

    @Getter
    private volatile RSAKey activeKey;
    private final Map<String, RSAKey> oldKeys = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() throws Exception {
        if (Files.exists(privateKeyPath()) && Files.exists(publicKeyPath())) {
            this.activeKey = loadKeyFromFiles("key-initial");
        } else {
            rotateKeys(); // اگر کلیدها موجود نبود، تولید اولیه
        }
    }

    public RSAKey loadKeyFromFiles(String keyId) throws Exception {
        // بارگذاری کلیدها از filesystem
        RSAPrivateKey privateKey = RsaKeyConverters.pkcs8()
                .convert(Files.newInputStream(privateKeyPath()));

        RSAPublicKey publicKey = RsaKeyConverters.x509()
                .convert(Files.newInputStream(publicKeyPath()));

        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(keyId)
                .build();
    }

    public synchronized void rotateKeys() throws Exception {
        if (activeKey != null) {
            oldKeys.put(activeKey.getKeyID(), activeKey);
        }

        // تولید کلید جدید
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(2048);
        KeyPair pair = gen.generateKeyPair();

        // تبدیل به PEM استاندارد
        String privatePem = PemUtil.toPrivatePem(pair.getPrivate());
        String publicPem  = PemUtil.toPublicPem(pair.getPublic());

        // ذخیره روی filesystem
        Files.writeString(privateKeyPath(), privatePem,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        Files.writeString(publicKeyPath(), publicPem,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        // بارگذاری کلید جدید
        RSAPrivateKey privateKey = RsaKeyConverters.pkcs8().convert(Files.newInputStream(privateKeyPath()));
        RSAPublicKey publicKey = RsaKeyConverters.x509().convert(Files.newInputStream(publicKeyPath()));

        this.activeKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    private Path privateKeyPath() {
        return keysDir.resolve("private_key.pem");
    }

    private Path publicKeyPath() {
        return keysDir.resolve("public_key.pem");
    }

    public Map<String, RSAKey> getAllKeys() {
        Map<String, RSAKey> map = new HashMap<>(oldKeys);
        if (activeKey != null) {
            map.put(activeKey.getKeyID(), activeKey);
        }
        return map;
    }
}
