package com.monitor.transaction.service;

import com.monitor.transaction.model.request.AuthRequest;
import com.monitor.transaction.model.response.LoginResponse;
import com.monitor.transaction.model.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerCustomer(AuthRequest authRequest);
    RegisterResponse registerAdmin(AuthRequest authRequest);
    LoginResponse login(AuthRequest authRequest);
}
