package com.monitor.transaction.model.entity;

import com.monitor.transaction.constant.DbPath;
import com.monitor.transaction.constant.ERole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = DbPath.ROLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private ERole name;
}
