package com.monitor.transaction.service.impl;

import com.monitor.transaction.constant.ERole;
import com.monitor.transaction.model.entity.Role;
import com.monitor.transaction.repository.RoleRepository;
import com.monitor.transaction.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;
    @Override
    public Role getOrSave(ERole role) {
        Optional<Role> optionalRole = repository.findByName(role);

        if (optionalRole.isPresent()){
            return optionalRole.get();
        }

        Role currentRole = Role.builder()
                .name(role)
                .build();

        return repository.saveAndFlush(currentRole);
    }
}
