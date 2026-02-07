package com.admin_ms.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    @Query("SELECT a FROM Admin a WHERE a.email = ?1")
    Optional<Admin> findByEmail(String email);

    @Query("SELECT a FROM Admin a WHERE a.email = ?1 AND a.password = ?2")
    Optional<Admin> findByEmailAndPassword(String email, String password);

    @Query("SELECT COUNT(a) > 0 FROM Admin a WHERE a.email = ?1")
    boolean existsByEmail(String email);
}