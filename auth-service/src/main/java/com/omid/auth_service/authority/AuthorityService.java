package com.omid.auth_service.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorityService {
    private final AuthorityRepository authorityRepository;

    public List<Authority> loadAll() {
        return authorityRepository.findAll();
    }

    public Authority loadOne(Long id) {
        return authorityRepository.findById(id).orElse(null);
    }

    public Authority create(Authority authority) {
        return authorityRepository.save(authority);
    }
}
