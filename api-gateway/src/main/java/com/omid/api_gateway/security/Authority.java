package com.omid.api_gateway.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Setter
@Getter
public class Authority implements GrantedAuthority {
    private Long id;

    private String authority;
}
