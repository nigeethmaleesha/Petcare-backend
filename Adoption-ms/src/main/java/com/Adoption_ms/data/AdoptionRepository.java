package com.Adoption_ms.data;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface AdoptionRepository extends JpaRepository<Adoption, Integer> {
    List<Adoption> findByShelterId(int shelterId);

}