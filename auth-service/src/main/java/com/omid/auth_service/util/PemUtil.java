package com.omid.auth_service.util;

import java.io.StringWriter;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class PemUtil {

    public static String toPrivatePem(Key privateKey) {
        String base64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        return wrapWithPemHeader(base64, "PRIVATE KEY");
    }

    public static String toPublicPem(Key publicKey) {
        String base64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        return wrapWithPemHeader(base64, "PUBLIC KEY");
    }

    private static String wrapWithPemHeader(String base64, String type) {
        StringBuilder sb = new StringBuilder();
        sb.append("-----BEGIN ").append(type).append("-----\n");

        // تقسیم خطی به 64 کاراکتر استاندارد PEM
        for (int i = 0; i < base64.length(); i += 64) {
            int end = Math.min(i + 64, base64.length());
            sb.append(base64, i, end).append("\n");
        }

        sb.append("-----END ").append(type).append("-----\n");
        return sb.toString();
    }
}
