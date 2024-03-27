package com.monitor.transaction.model.request;

import com.monitor.transaction.constant.EType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private String idBank;
    private String idTransaction;
    private String transDate;
    private String type;
    private long nominal;
    private long saldo;
    private String description;
}
