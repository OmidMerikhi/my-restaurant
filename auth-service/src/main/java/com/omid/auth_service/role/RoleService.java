package com.omid.auth_service.role;

import com.omid.auth_service.authority.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> loadAll() {
        return roleRepository.findAll();
    }

    public Role loadOne(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Role create(Role role) {
        return roleRepository.save(role);
    }
}
