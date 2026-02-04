package com.petowner_ms.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface VaccinationRepository extends JpaRepository<Vaccination, Integer> {

    @Query("SELECT v FROM Vaccination v WHERE v.petId = ?1 ORDER BY v.vaccinationDate DESC")
    List<Vaccination> findByPetId(int petId);

    @Query("SELECT v FROM Vaccination v WHERE v.petId = ?1 AND v.status = ?2")
    List<Vaccination> findByPetIdAndStatus(int petId, String status);

    @Query("SELECT v FROM Vaccination v WHERE v.petId IN (SELECT p.petId FROM Pet p WHERE p.petOwnerId = ?1)")
    List<Vaccination> findByOwnerId(int petOwnerId);
}