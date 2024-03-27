package com.monitor.transaction.controller;

import com.monitor.transaction.constant.AppPath;
import com.monitor.transaction.model.request.BankRequest;
import com.monitor.transaction.model.request.TransactionRequest;
import com.monitor.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = AppPath.TRANSACTION)
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TransactionRequest transactionRequest) throws SQLException {
        return transactionService.create(transactionRequest);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody TransactionRequest transactionRequest) throws SQLException {
        return transactionService.update(transactionRequest);
    }

}
