package com.monitor.transaction.controller;

import com.monitor.transaction.constant.AppPath;
import com.monitor.transaction.model.entity.Bank;
import com.monitor.transaction.model.request.BankRequest;
import com.monitor.transaction.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = AppPath.BANK)
public class BankController {

    private final BankService bankService;
    @PostMapping
    public ResponseEntity<?> create(@RequestBody BankRequest bank) throws SQLException {
        return bankService.create(bank);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody BankRequest bank) throws SQLException {
        return bankService.update(bank);
    }

    @GetMapping
    public ResponseEntity<?> getAll() throws SQLException {
        return bankService.getAll();
    }

    @GetMapping(AppPath.ID)
    public ResponseEntity<?> getById(@PathVariable String id) throws SQLException {
        return bankService.getById(id);
    }
}
