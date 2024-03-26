package com.monitor.transaction.model.entity;

import com.monitor.transaction.constant.DbPath;
import com.monitor.transaction.constant.EType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = DbPath.TRANSACTION)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "trans_date")
    private String transDate;

    @Column(name = "type")
    private EType type;

    @Column(name = "description")
    private String description;

    @Column(name = "nominal")
    private long nominal;

    @Column(name = "saldo")
    private long saldo;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

}
