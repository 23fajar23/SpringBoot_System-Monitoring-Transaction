package com.monitor.transaction.service;

import com.monitor.transaction.model.entity.Bank;
import com.monitor.transaction.model.request.BankRequest;
import com.monitor.transaction.model.response.BankResponse;
import com.monitor.transaction.model.response.DetailBankResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;

public interface BankService {
    ResponseEntity<?> create(BankRequest bankRequest) throws SQLException;
    ResponseEntity<?> update(BankRequest bankRequest) throws SQLException;
    boolean checkRequest(BankRequest bankRequest, String type);
    ResponseEntity<?> getAll() throws SQLException;
    ResponseEntity<?> getById(String id) throws SQLException;
    ResponseEntity<?> toResponseEntity(String message, HttpStatus statusCode, BankResponse bankResponse);
    ResponseEntity<?> toResponseEntityList(String message, HttpStatus statusCode, List<DetailBankResponse> bankResponse);
}
