package com.monitor.transaction.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BankRequest {
    private String idBank;
    private String service;
    private String noRekening;
    private String customer_id;
}
