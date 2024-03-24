package com.monitor.transaction.repository;

import com.monitor.transaction.constant.ERole;
import com.monitor.transaction.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {
    Optional<Role> findByName(ERole name);
}
