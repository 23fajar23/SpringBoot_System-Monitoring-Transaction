package com.monitor.transaction.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class DetailBankResponse {
    private String idBank;
    private String name;
    private String service;
    private String noRekening;
    private List<TransactionResponse> transaction;
}
