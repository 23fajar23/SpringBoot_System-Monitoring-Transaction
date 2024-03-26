package com.monitor.transaction.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.monitor.transaction.constant.DbPath;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = DbPath.BANK)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "service",nullable = false)
    private String service;

    @Column(name = "no_rekening", nullable = false)
    private String noRekening;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.PERSIST)
    private List<Transaction> transactions;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Customer customer;

}
