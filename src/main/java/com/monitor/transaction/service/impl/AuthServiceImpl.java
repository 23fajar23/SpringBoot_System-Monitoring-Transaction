package com.monitor.transaction.service.impl;

import com.monitor.transaction.constant.ERole;
import com.monitor.transaction.model.entity.*;
import com.monitor.transaction.model.request.AuthRequest;
import com.monitor.transaction.model.response.LoginResponse;
import com.monitor.transaction.model.response.RegisterResponse;
import com.monitor.transaction.repository.UserCredentialRepository;
import com.monitor.transaction.security.JwtUtil;
import com.monitor.transaction.service.AdminService;
import com.monitor.transaction.service.AuthService;
import com.monitor.transaction.service.CustomerService;
import com.monitor.transaction.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final CustomerService customerService;
    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    private final RoleService roleService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse registerCustomer(AuthRequest request) {
        try {
            //TODO 1 : set Role
            Role role = roleService.getOrSave(ERole.ROLE_CUSTOMER);

            //TODO 2 : set credential
            UserCredential userCredential = UserCredential.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            //TODO 3 : set Customer
            Customer customer = Customer.builder()
                    .name(request.getName())
                    .address(request.getAddress())
                    .mobilePhone(request.getMobilePhone())
                    .userCredential(userCredential)
                    .build();
            customerService.save(customer);

            return RegisterResponse.builder()
                    .email(userCredential.getEmail())
                    .role(userCredential.getRole().getName().toString())
                    .build();

        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user already exist");
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse registerAdmin(AuthRequest request) {
        try {
            //TODO 1 : set Role
            Role role = roleService.getOrSave(ERole.ROLE_ADMIN);

            //TODO 2 : set credential
            UserCredential userCredential = UserCredential.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            //TODO 3 : set Customer
            Admin admin = Admin.builder()
                    .name(request.getName())
                    .phoneNumber(request.getMobilePhone())
                    .build();

            adminService.save(admin);

            return RegisterResponse.builder()
                    .email(userCredential.getEmail())
                    .role(userCredential.getRole().getName().toString())
                    .build();

        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user already exist");
        }
    }

    @Override
    public LoginResponse login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(
                authRequest.getEmail().toLowerCase(),
                authRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUser appUser = (AppUser) authentication.getPrincipal();
        String token = jwtUtil.generateToken(appUser);

        return LoginResponse.builder()
                .email(authRequest.getEmail())
                .token(token)
                .role(appUser.getRole().name())
                .build();
    }
}
