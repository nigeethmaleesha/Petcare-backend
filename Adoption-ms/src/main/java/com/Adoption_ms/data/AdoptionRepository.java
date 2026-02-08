package com.Adoption_ms.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AdoptionRepository extends JpaRepository<Adoption, String> {

    List<Adoption> findByShelterId(String shelterId);

    @Query("SELECT a.adoption_id FROM Adoption a ORDER BY a.adoption_id DESC")
    List<String> findLastAdoptionId();
}
