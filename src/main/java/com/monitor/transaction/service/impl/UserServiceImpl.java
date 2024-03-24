package com.monitor.transaction.service.impl;

import com.monitor.transaction.model.entity.AppUser;
import com.monitor.transaction.model.entity.UserCredential;
import com.monitor.transaction.repository.UserCredentialRepository;
import com.monitor.transaction.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserCredentialRepository userCredentialRepository;
    @Override
    public AppUser loadUserByUserId(String id) {
        UserCredential userCredential = userCredentialRepository.findById(id).orElseThrow();
        return AppUser.builder()
                .id(userCredential.getId())
                .username(userCredential.getEmail())
                .password(userCredential.getPassword())
                .role(userCredential.getRole().getName())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential userCredential = userCredentialRepository.findByEmail(username).orElseThrow();
        return AppUser.builder()
                .id(userCredential.getId())
                .username(userCredential.getEmail())
                .password(userCredential.getPassword())
                .role(userCredential.getRole().getName())
                .build();
    }
}
