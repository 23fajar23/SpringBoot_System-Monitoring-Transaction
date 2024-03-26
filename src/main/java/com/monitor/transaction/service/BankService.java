package com.monitor.transaction.service;

import com.monitor.transaction.model.entity.Bank;
import com.monitor.transaction.model.request.BankRequest;
import com.monitor.transaction.model.response.BankResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

public interface BankService {
    ResponseEntity<?> create(BankRequest bankRequest) throws SQLException;
    ResponseEntity<?> update(BankRequest bankRequest) throws SQLException;
    boolean checkRequest(BankRequest bankRequest, String type);
    ResponseEntity<?> toResponseEntity(String message, HttpStatus statusCode, BankResponse bankResponse);
}
