package com.monitor.transaction.model.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private String transDate;
    private String type;
    private long nominal;
    private long saldo;
    private String description;
}
