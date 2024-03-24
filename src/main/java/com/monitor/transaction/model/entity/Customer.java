package com.monitor.transaction.model.entity;

import com.monitor.transaction.constant.DbPath;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = DbPath.CUSTOMER)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = true, length = 50)
    private String name;

    @Column(name = "address", nullable = true, length = 50)
    private String address;

    @Column(name = "phone", nullable = true, unique = true ,length = 50)
    private String mobilePhone;

    @OneToOne
    @JoinColumn(name = "user_credential_id")
    private UserCredential userCredential;
}
