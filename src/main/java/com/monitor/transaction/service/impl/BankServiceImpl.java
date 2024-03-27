package com.monitor.transaction.service.impl;

import com.monitor.transaction.model.entity.Bank;
import com.monitor.transaction.model.entity.Customer;
import com.monitor.transaction.model.request.BankRequest;
import com.monitor.transaction.model.response.BankResponse;
import com.monitor.transaction.model.response.CommonResponse;
import com.monitor.transaction.model.response.DetailBankResponse;
import com.monitor.transaction.model.response.TransactionResponse;
import com.monitor.transaction.repository.BankRepository;
import com.monitor.transaction.service.BankService;
import com.monitor.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    private final TransactionService transactionService;

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

        Bank newBank = bankRepository.createBank(data);
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

        Bank findBank = bankRepository.findBankById(bankRequest.getIdBank());

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

        Bank updateBank = bankRepository.updateBank(data);
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
    public ResponseEntity<?> getAll() throws SQLException {
        List<DetailBankResponse> detailBankResponses = bankRepository.findAllBank()
                .stream()
                .map(
                        bank -> {
                            try {
                                return DetailBankResponse.builder()
                                        .idBank(bank.getId())
                                        .service(bank.getService())
                                        .noRekening(bank.getNoRekening())
                                        .name(bankRepository.findCustomerById(bank.getCustomer().getId()).getName())
                                        .transaction(transactionService.getTransactionByBankId(bank.getId()))
                                        .build();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .toList();
        return toResponseEntityList("Successfully fetch bank data",HttpStatus.OK,detailBankResponses);
    }

    @Override
    public ResponseEntity<?> getById(String id) throws SQLException {
        Bank bank = bankRepository.findBankById(id);
        if (bank == null){
            return toResponseEntity("Bank id not found",HttpStatus.CREATED,null);
        }
        Customer customer = bankRepository.findCustomerById(bank.getCustomer().getId());

        DetailBankResponse detailBankResponse = DetailBankResponse.builder()
                .idBank(bank.getId())
                .name(customer.getName())
                .service(bank.getService())
                .noRekening(bank.getNoRekening())
                .transaction(transactionService.getTransactionByBankId(bank.getId()))
                .build();

        return toResponseEntityList("Successfully fetch bank data",HttpStatus.OK, List.of(detailBankResponse));
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

    @Override
    public ResponseEntity<?> toResponseEntityList(String message, HttpStatus statusCode, List<DetailBankResponse> bankResponses) {
        CommonResponse<List<DetailBankResponse>> response = CommonResponse.<List<DetailBankResponse>>builder()
                .message(message)
                .statusCode(statusCode.value())
                .data(bankResponses)
                .build();

        return ResponseEntity
                .status(statusCode)
                .body(response);
    }


}
