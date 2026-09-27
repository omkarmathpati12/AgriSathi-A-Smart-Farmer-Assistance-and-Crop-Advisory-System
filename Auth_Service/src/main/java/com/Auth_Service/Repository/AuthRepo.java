package com.Auth_Service.Repository;

import com.Auth_Service.Entity.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepo extends JpaRepository<AuthEntity, Long> {
    boolean existsByEmail(String email);
    Optional<AuthEntity> findByEmail(String email);
}
