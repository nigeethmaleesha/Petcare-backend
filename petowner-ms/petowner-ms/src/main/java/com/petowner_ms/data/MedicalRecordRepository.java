package com.petowner_ms.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {

    @Query("SELECT m FROM MedicalRecord m WHERE m.petId = ?1 ORDER BY m.recordDate DESC")
    List<MedicalRecord> findByPetId(int petId);

    @Query("SELECT m FROM MedicalRecord m WHERE m.petId = ?1 AND m.recordType = ?2")
    List<MedicalRecord> findByPetIdAndType(int petId, String recordType);

    @Query("SELECT m FROM MedicalRecord m WHERE m.petId = ?1 AND LOWER(m.conditionName) LIKE LOWER(CONCAT('%', ?2, '%'))")
    List<MedicalRecord> searchByCondition(int petId, String condition);
}