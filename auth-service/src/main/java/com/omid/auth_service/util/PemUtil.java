package com.omid.auth_service.util;

import java.security.Key;
import java.util.Base64;

public class PemUtil {
    public static String toPrivatePem(Key privateKey) {
        // MIME encoder با line length 64
        String base64 = Base64.getMimeEncoder(64, "\n".getBytes())
                .encodeToString(privateKey.getEncoded());
        return wrapWithPemHeader(base64, "PRIVATE KEY");
    }

    public static String toPublicPem(Key publicKey) {
        String base64 = Base64.getMimeEncoder(64, "\n".getBytes())
                .encodeToString(publicKey.getEncoded());
        return wrapWithPemHeader(base64, "PUBLIC KEY");
    }

    private static String wrapWithPemHeader(String base64, String type) {
        return "-----BEGIN " + type + "-----\n" +
                base64 +
                "\n-----END " + type + "-----\n";
    }
}
