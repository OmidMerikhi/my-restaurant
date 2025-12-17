package com.omid.auth_service.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class PemUtil {

//    public static RSAPrivateKey readPrivateKey(String pem)
//            throws Exception {
//
//        pem = pem.replaceAll(
//                        "-----BEGIN (.*)-----", "")
//                .replaceAll(
//                        "-----END (.*)-----", "")
//                .replaceAll("\\s", "");
//
//        byte[] decoded = Base64.getDecoder().decode(pem);
//        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
//        return (RSAPrivateKey)
//                KeyFactory.getInstance("RSA")
//                        .generatePrivate(spec);
//    }
//
//    public static RSAPublicKey readPublicKey(String pem)
//            throws Exception {
//
//        pem = pem.replaceAll(
//                        "-----BEGIN (.*)-----", "")
//                .replaceAll(
//                        "-----END (.*)-----", "")
//                .replaceAll("\\s", "");
//
//        byte[] decoded = Base64.getDecoder().decode(pem);
//        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
//        return (RSAPublicKey)
//                KeyFactory.getInstance("RSA")
//                        .generatePublic(spec);
//    }
//    public static String toPrivatePem(Key privateKey) {
//        // MIME encoder با line length 64
//        String base64 = Base64.getMimeEncoder(64, "\n".getBytes())
//                .encodeToString(privateKey.getEncoded());
//        return wrapWithPemHeader(base64, "PRIVATE KEY");
//    }
//
//    public static String toPublicPem(Key publicKey) {
//        String base64 = Base64.getMimeEncoder(64, "\n".getBytes())
//                .encodeToString(publicKey.getEncoded());
//        return wrapWithPemHeader(base64, "PUBLIC KEY");
//    }
//
//    private static String wrapWithPemHeader(String base64, String type) {
//        return "-----BEGIN " + type + "-----\n" +
//                base64 +
//                "\n-----END " + type + "-----\n";
//    }
}
