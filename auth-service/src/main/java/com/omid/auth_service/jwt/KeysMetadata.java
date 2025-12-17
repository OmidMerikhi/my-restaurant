package com.omid.auth_service.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class KeysMetadata {
    private String activeKid;
    private List<KeyEntry> keys;

    @Data
    public static class KeyEntry {
        private String kid;
        private Instant createdAt;
    }
}
