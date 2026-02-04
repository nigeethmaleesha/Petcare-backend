package com.Adoption_ms.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Integer> {
    // JpaRepository provides all CRUD methods by default
    List<AdoptionRequest> findByShelterId(int shelterId);

}
