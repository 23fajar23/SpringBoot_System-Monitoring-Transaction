package com.monitor.transaction.service;

import com.monitor.transaction.model.request.TransactionRequest;
import com.monitor.transaction.model.response.TransactionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;

public interface TransactionService {
    ResponseEntity<?> create(TransactionRequest transactionRequest) throws SQLException;
    ResponseEntity<?> update(TransactionRequest transactionRequest) throws SQLException;
    boolean checkRequest(TransactionRequest transactionRequest, String type);
    List<TransactionResponse> getTransactionByBankId(String id) throws SQLException;
    ResponseEntity<?> toResponseEntity(String message, HttpStatus statusCode, TransactionResponse transactionResponse);
}
