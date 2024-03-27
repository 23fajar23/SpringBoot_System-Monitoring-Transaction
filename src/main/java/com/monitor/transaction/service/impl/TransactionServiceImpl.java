package com.monitor.transaction.service.impl;

import com.monitor.transaction.constant.EType;
import com.monitor.transaction.model.entity.Bank;
import com.monitor.transaction.model.entity.Transaction;
import com.monitor.transaction.model.request.TransactionRequest;
import com.monitor.transaction.model.response.CommonResponse;
import com.monitor.transaction.model.response.TransactionResponse;
import com.monitor.transaction.repository.BankRepository;
import com.monitor.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final BankRepository bankRepository;

    @Override
    public ResponseEntity<?> create(TransactionRequest transactionRequest) throws SQLException {
        if (!checkRequest(transactionRequest, "CREATE")){
            return toResponseEntity("Failed to add new transaction",HttpStatus.CREATED,null);
        }

        Bank bank = bankRepository.findBankById(transactionRequest.getIdBank());
        if (bank == null){
            return toResponseEntity("Bank id not found",HttpStatus.CREATED,null);
        }

        Transaction transaction = Transaction.builder()
                .transDate(transactionRequest.getTransDate())
                .type(EType.valueOf(transactionRequest.getType()))
                .description(transactionRequest.getDescription())
                .nominal(transactionRequest.getNominal())
                .saldo(transactionRequest.getSaldo())
                .bank(bank)
                .build();

        Transaction newTransaction = bankRepository.createTransaction(transaction);
        TransactionResponse transactionResponse = TransactionResponse.builder()
                .idTransaction(newTransaction.getId())
                .transDate(newTransaction.getTransDate())
                .type(newTransaction.getType())
                .nominal(newTransaction.getNominal())
                .saldo(newTransaction.getSaldo())
                .description(newTransaction.getDescription())
                .build();

        return toResponseEntity("Successfully add new transaction",HttpStatus.CREATED,transactionResponse);
    }

    @Override
    public ResponseEntity<?> update(TransactionRequest transactionRequest) throws SQLException {
        if (!checkRequest(transactionRequest, "UPDATE")){
            return toResponseEntity("Failed to update transaction",HttpStatus.CREATED,null);
        }

        Transaction transaction = bankRepository.findTransactionById(transactionRequest.getIdTransaction());
        if (transaction == null){
            return toResponseEntity("Transaction id not found",HttpStatus.CREATED,null);
        }

        transaction.setNominal(transactionRequest.getNominal());
        transaction.setSaldo(transactionRequest.getSaldo());
        transaction.setType(EType.valueOf(transactionRequest.getType()));
        transaction.setTransDate(transactionRequest.getTransDate());
        transaction.setDescription(transactionRequest.getDescription());

        Transaction newTransaction = bankRepository.updateTransaction(transaction);
        TransactionResponse transactionResponse = TransactionResponse.builder()
                .idTransaction(newTransaction.getId())
                .transDate(newTransaction.getTransDate())
                .type(newTransaction.getType())
                .nominal(newTransaction.getNominal())
                .saldo(newTransaction.getSaldo())
                .description(newTransaction.getDescription())
                .build();

        return toResponseEntity("Successfully add new transaction",HttpStatus.CREATED,transactionResponse);
    }

    @Override
    public boolean checkRequest(TransactionRequest transactionRequest, String type) {
        boolean validate = true;

        if (type.equals("CREATE") && transactionRequest.getIdBank() == null){
            validate = false;
        }else if (!Objects.equals(transactionRequest.getType(), EType.DEBIT.toString()) && !Objects.equals(transactionRequest.getType(), EType.CREDIT.toString())){
            validate = false;
        }else if (transactionRequest.getNominal() < 0){
            validate = false;
        }else if (transactionRequest.getSaldo() < 0){
            validate = false;
        }

        try {
            LocalDateTime ignored = LocalDateTime.parse(transactionRequest.getTransDate());
        }catch (Exception e){
            validate = false;
        }

        return validate;
    }

    @Override
    public List<TransactionResponse> getTransactionByBankId(String id) throws SQLException {
        return bankRepository.findTransactionByBankId(id)
                .stream()
                .map(
                        transaction -> {
                            return TransactionResponse.builder()
                                    .idTransaction(transaction.getId())
                                    .transDate(transaction.getTransDate())
                                    .type(transaction.getType())
                                    .nominal(transaction.getNominal())
                                    .saldo(transaction.getSaldo())
                                    .description(transaction.getDescription())
                                    .build();
                        }
                )
                .toList();
    }

    @Override
    public ResponseEntity<?> toResponseEntity(String message, HttpStatus statusCode, TransactionResponse transactionResponse) {
        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .message(message)
                .statusCode(statusCode.value())
                .data(transactionResponse)
                .build();

        return ResponseEntity
                .status(statusCode)
                .body(response);
    }
}
