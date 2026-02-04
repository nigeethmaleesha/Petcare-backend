package com.petowner_ms.controller;

import com.petowner_ms.data.GrowthRecord;
import com.petowner_ms.dto.GrowthRecordDTO;
import com.petowner_ms.service.GrowthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/pets/{petId}/growth")
public class GrowthRecordController {

    @Autowired
    private GrowthRecordService growthRecordService;

    // Add growth record
    @PostMapping
    public ResponseEntity<?> addGrowthRecord(
            @RequestHeader("X-User-Id") int userId,
            @PathVariable int petId,
            @RequestBody GrowthRecordDTO growthRecordDTO) {
        try {
            GrowthRecord growthRecord = growthRecordService.addGrowthRecord(petId, userId, growthRecordDTO);
            if (growthRecord != null) {
                return ResponseEntity.ok(Map.of(
                        "message", "Growth record added successfully",
                        "growthId", growthRecord.getGrowthId()
                ));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Pet not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to add growth record"));
        }
    }

    // Get all growth records for a pet
    @GetMapping
    public ResponseEntity<?> getGrowthRecords(
            @RequestHeader("X-User-Id") int userId,
            @PathVariable int petId) {
        try {
            List<GrowthRecord> growthRecords = growthRecordService.getGrowthRecordsByPetId(petId, userId);
            if (growthRecords != null) {
                return ResponseEntity.ok(growthRecords);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Pet not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch growth records"));
        }
    }

    // Get latest growth record
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestGrowthRecord(
            @RequestHeader("X-User-Id") int userId,
            @PathVariable int petId) {
        try {
            GrowthRecord latestRecord = growthRecordService.getLatestGrowthRecord(petId, userId);
            if (latestRecord != null) {
                return ResponseEntity.ok(latestRecord);
            } else {
                return ResponseEntity.ok(Map.of("message", "No growth records found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch growth record"));
        }
    }

    // Delete growth record
    @DeleteMapping("/{growthId}")
    public ResponseEntity<?> deleteGrowthRecord(
            @RequestHeader("X-User-Id") int userId,
            @PathVariable int petId,
            @PathVariable int growthId) {
        try {
            boolean deleted = growthRecordService.deleteGrowthRecord(growthId, userId);
            if (deleted) {
                return ResponseEntity.ok(Map.of("message", "Growth record deleted"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Growth record not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete growth record"));
        }
    }
}