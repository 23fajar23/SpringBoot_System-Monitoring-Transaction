package com.monitor.transaction.service.impl;

import com.monitor.transaction.model.entity.Bank;
import com.monitor.transaction.model.entity.Customer;
import com.monitor.transaction.model.request.BankRequest;
import com.monitor.transaction.model.response.BankResponse;
import com.monitor.transaction.model.response.CommonResponse;
import com.monitor.transaction.repository.BankRepository;
import com.monitor.transaction.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;

    @Override
    public ResponseEntity<?> create(BankRequest bankRequest) throws SQLException {
        if (!checkRequest(bankRequest, "CREATE")){
            return toResponseEntity("Failed to add new bank",HttpStatus.CREATED,null);
        }

        Customer customer = bankRepository.findCustomerById(bankRequest.getCustomer_id());

        if (customer == null){
            return toResponseEntity("Customer id not found",HttpStatus.CREATED,null);
        }

        Bank data = Bank.builder()
                .service(bankRequest.getService())
                .noRekening(bankRequest.getNoRekening())
                .customer(customer)
                .build();

        Bank newBank = bankRepository.create(data);
        BankResponse bankResponse = BankResponse.builder()
                .name(customer.getName())
                .service(newBank.getService())
                .noRekening(newBank.getNoRekening())
                .build();

        return toResponseEntity("Successfully add new bank account",HttpStatus.CREATED,bankResponse);
    }

    @Override
    public ResponseEntity<?> update(BankRequest bankRequest) throws SQLException {
        if (!checkRequest(bankRequest, "UPDATE")){
            return toResponseEntity("Failed to fetch data",HttpStatus.CREATED,null);
        }

        Bank findBank = bankRepository.findById(bankRequest.getIdBank());

        if (findBank == null){
            return toResponseEntity("Bank id not found",HttpStatus.CREATED,null);
        }

        Customer customer = bankRepository.findCustomerById(findBank.getCustomer().getId());
        Bank data = Bank.builder()
                .id(bankRequest.getIdBank())
                .service(bankRequest.getService())
                .noRekening(bankRequest.getNoRekening())
                .customer(customer)
                .build();

        Bank updateBank = bankRepository.update(data);
        BankResponse bankResponse = BankResponse.builder()
                .name(customer.getName())
                .service(updateBank.getService())
                .noRekening(updateBank.getNoRekening())
                .build();

        return toResponseEntity("Successfully update bank account",HttpStatus.CREATED,bankResponse);
    }

    @Override
    public boolean checkRequest(BankRequest bankRequest, String type) {
        boolean validate = true;

        if (bankRequest.getService() == null){
            validate = false;
        }
        else if (bankRequest.getNoRekening() == null){
            validate = false;
        }else if (type.equals("UPDATE") && bankRequest.getIdBank() == null) {
            validate = false;
        }

        return validate;
    }

    @Override
    public ResponseEntity<?> toResponseEntity(String message, HttpStatus statusCode, BankResponse bankResponse) {
        CommonResponse<BankResponse> response = CommonResponse.<BankResponse>builder()
                .message(message)
                .statusCode(statusCode.value())
                .data(bankResponse)
                .build();

        return ResponseEntity
                .status(statusCode)
                .body(response);
    }


}
