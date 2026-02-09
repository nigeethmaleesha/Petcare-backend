package com.Adoption_ms.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, String> {

    List<AdoptionRequest> findByShelterId(String shelterId);

    @Query("SELECT r.request_id FROM AdoptionRequest r ORDER BY r.request_id DESC")
    List<String> findLastId();
}
