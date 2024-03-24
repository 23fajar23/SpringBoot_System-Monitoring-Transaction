package com.monitor.transaction.service.impl;

import com.monitor.transaction.model.entity.Admin;
import com.monitor.transaction.repository.AdminRepository;
import com.monitor.transaction.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    @Override
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }
}
