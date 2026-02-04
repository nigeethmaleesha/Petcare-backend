package com.petowner_ms.controller;

import com.petowner_ms.data.Vaccination;
import com.petowner_ms.dto.VaccinationDTO;
import com.petowner_ms.service.VaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/pets/{petId}/vaccinations")
public class VaccinationController {

    @Autowired
    private VaccinationService vaccinationService;

    // Add vaccination for a pet
    @PostMapping
    public ResponseEntity<?> addVaccination(
            @RequestHeader("X-User-Id") int userId,
            @PathVariable int petId,
            @RequestBody VaccinationDTO vaccinationDTO) {
        try {
            Vaccination vaccination = vaccinationService.addVaccination(petId, userId, vaccinationDTO);
            if (vaccination != null) {
                return ResponseEntity.ok(Map.of(
                        "message", "Vaccination added successfully",
                        "vaccinationId", vaccination.getVaccinationId()
                ));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Pet not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to add vaccination"));
        }
    }

    // Get all vaccinations for a pet
    @GetMapping
    public ResponseEntity<?> getVaccinations(
            @RequestHeader("X-User-Id") int userId,
            @PathVariable int petId) {
        try {
            List<Vaccination> vaccinations = vaccinationService.getVaccinationsByPetId(petId, userId);
            if (vaccinations != null) {
                return ResponseEntity.ok(vaccinations);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Pet not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch vaccinations"));
        }
    }

    // Update vaccination status
    @PatchMapping("/{vaccinationId}/status")
    public ResponseEntity<?> updateVaccinationStatus(
            @RequestHeader("X-User-Id") int userId,
            @PathVariable int petId,
            @PathVariable int vaccinationId,
            @RequestBody Map<String, String> request) {
        try {
            String status = request.get("status");
            Vaccination vaccination = vaccinationService.updateVaccinationStatus(vaccinationId, userId, status);
            if (vaccination != null) {
                return ResponseEntity.ok(Map.of("message", "Vaccination status updated"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Vaccination not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update vaccination status"));
        }
    }

    // Delete vaccination
    @DeleteMapping("/{vaccinationId}")
    public ResponseEntity<?> deleteVaccination(
            @RequestHeader("X-User-Id") int userId,
            @PathVariable int petId,
            @PathVariable int vaccinationId) {
        try {
            boolean deleted = vaccinationService.deleteVaccination(vaccinationId, userId);
            if (deleted) {
                return ResponseEntity.ok(Map.of("message", "Vaccination deleted"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Vaccination not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete vaccination"));
        }
    }
}