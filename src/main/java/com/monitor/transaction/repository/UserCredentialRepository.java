package com.monitor.transaction.repository;

import com.monitor.transaction.model.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential,String> {
    Optional<UserCredential> findByEmail(String email);
}
