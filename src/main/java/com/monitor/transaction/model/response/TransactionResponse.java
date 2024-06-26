package com.monitor.transaction.model.response;

import com.monitor.transaction.constant.EType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private String idTransaction;
    private String transDate;
    private EType type;
    private long nominal;
    private long saldo;
    private String description;
}
