package com.petowner_ms.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface GrowthRecordRepository extends JpaRepository<GrowthRecord, Integer> {

    @Query("SELECT g FROM GrowthRecord g WHERE g.petId = ?1 ORDER BY g.measurementDate DESC")
    List<GrowthRecord> findByPetId(int petId);

    @Query("SELECT g FROM GrowthRecord g WHERE g.petId = ?1 ORDER BY g.measurementDate DESC LIMIT 1")
    GrowthRecord findLatestByPetId(int petId);
}