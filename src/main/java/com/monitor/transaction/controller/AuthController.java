package com.monitor.transaction.controller;

import com.monitor.transaction.constant.AppPath;
import com.monitor.transaction.model.request.AuthRequest;
import com.monitor.transaction.model.response.CommonResponse;
import com.monitor.transaction.model.response.LoginResponse;
import com.monitor.transaction.model.response.RegisterResponse;
import com.monitor.transaction.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = AppPath.API + AppPath.AUTH)
public class AuthController {

    private final AuthService authService;

    @PostMapping(AppPath.REGISTER + AppPath.CUSTOMER)
    public ResponseEntity<?> registerCustomer(@RequestBody AuthRequest authRequest){
        RegisterResponse registerResponse = authService.registerCustomer(authRequest);

        CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .message("Successfully register new customer")
                .statusCode(HttpStatus.CREATED.value())
                .data(registerResponse)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping(AppPath.REGISTER + AppPath.ADMIN)
    public ResponseEntity<?> registerAdmin(@RequestBody AuthRequest authRequest){
        RegisterResponse registerResponse = authService.registerAdmin(authRequest);

        CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .message("Successfully register new customer")
                .statusCode(HttpStatus.CREATED.value())
                .data(registerResponse)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping(AppPath.LOGIN)
    public ResponseEntity<?> login(@RequestBody AuthRequest request){
        LoginResponse loginResponse = authService.login(request);
        CommonResponse<LoginResponse> response = CommonResponse.<LoginResponse>builder()
                .message("Success Login")
                .statusCode(HttpStatus.OK.value())
                .data(loginResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
