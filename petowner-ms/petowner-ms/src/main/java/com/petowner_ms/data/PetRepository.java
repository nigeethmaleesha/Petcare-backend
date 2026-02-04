package com.petowner_ms.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Integer> {

    @Query("SELECT p FROM Pet p WHERE p.petOwnerId = ?1")
    List<Pet> findByPetOwnerId(int petOwnerId);

    @Query("SELECT p FROM Pet p WHERE p.petId = ?1 AND p.petOwnerId = ?2")
    Pet findByPetIdAndOwnerId(int petId, int petOwnerId);
}