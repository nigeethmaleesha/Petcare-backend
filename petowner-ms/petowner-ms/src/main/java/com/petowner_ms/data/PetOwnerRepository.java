package com.petowner_ms.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetOwnerRepository extends JpaRepository<PetOwner, Integer> {

    @Query("SELECT p FROM PetOwner p WHERE p.email = ?1")
    Optional<PetOwner> findByEmail(String email);

    @Query("SELECT p FROM PetOwner p WHERE p.email = ?1 AND p.password = ?2")
    Optional<PetOwner> findByEmailAndPassword(String email, String password);

    @Query("SELECT COUNT(p) > 0 FROM PetOwner p WHERE p.email = ?1")
    boolean existsByEmail(String email);
}