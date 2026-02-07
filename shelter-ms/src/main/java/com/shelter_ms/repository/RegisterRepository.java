package com.shelter_ms.repository;

import com.shelter_ms.entity.Register;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface RegisterRepository extends JpaRepository<Register, Integer> {

    List<Register> findByStatus(String status);

    Optional<Register> findByEmail(String email);

    long countByStatus(String status);

}
