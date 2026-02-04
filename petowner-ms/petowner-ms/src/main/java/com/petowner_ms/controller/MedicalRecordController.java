package com.petowner_ms.controller;

import com.petowner_ms.data.MedicalRecord;
import com.petowner_ms.dto.MedicalRecordDTO;
import com.petowner_ms.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/pets/{petId}/medical")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    // Add medical record
    @PostMapping
    public ResponseEntity<?> addMedicalRecord(
            @RequestHeader("X-User-Id") int userId,
            @PathVariable int petId,
            @RequestBody MedicalRecordDTO medicalRecordDTO) {
        try {
            MedicalRecord medicalRecord = medicalRecordService.addMedicalRecord(petId, userId, medicalRecordDTO);
            if (medicalRecord != null) {
                return ResponseEntity.ok(Map.of(
                        "message", "Medical record added successfully",
                        "medicalId", medicalRecord.getMedicalId()
                ));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Pet not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to add medical record"));
        }
    }

    // Get all medical records for a pet
    @GetMapping
    public ResponseEntity<?> getMedicalRecords(
            @RequestHeader("X-User-Id") int userId,
            @PathVariable int petId) {
        try {
            List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPetId(petId, userId);
            if (medicalRecords != null) {
                return ResponseEntity.ok(medicalRecords);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Pet not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch medical records"));
        }
    }

    // Search medical records
    @GetMapping("/search")
    public ResponseEntity<?> searchMedicalRecords(
            @RequestHeader("X-User-Id") int userId,
            @PathVariable int petId,
            @RequestParam String condition) {
        try {
            List<MedicalRecord> medicalRecords = medicalRecordService.searchMedicalRecords(petId, userId, condition);
            if (medicalRecords != null) {
                return ResponseEntity.ok(medicalRecords);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Pet not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to search medical records"));
        }
    }

    // Delete medical record
    @DeleteMapping("/{medicalId}")
    public ResponseEntity<?> deleteMedicalRecord(
            @RequestHeader("X-User-Id") int userId,
            @PathVariable int petId,
            @PathVariable int medicalId) {
        try {
            boolean deleted = medicalRecordService.deleteMedicalRecord(medicalId, userId);
            if (deleted) {
                return ResponseEntity.ok(Map.of("message", "Medical record deleted"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Medical record not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete medical record"));
        }
    }
}