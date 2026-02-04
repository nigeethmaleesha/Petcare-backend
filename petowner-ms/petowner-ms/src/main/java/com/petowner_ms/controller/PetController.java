package com.petowner_ms.controller;

import com.petowner_ms.data.Pet;
import com.petowner_ms.dto.PetDTO;
import com.petowner_ms.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetService petService;

    // Add a new pet
    @PostMapping
    public ResponseEntity<?> addPet(@RequestHeader("X-User-Id") int userId, @RequestBody PetDTO petDTO) {
        try {
            Pet pet = petService.addPet(userId, petDTO);
            if (pet != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Pet added successfully");
                response.put("petId", pet.getPetId());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "Failed to add pet"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to add pet: " + e.getMessage()));
        }
    }

    // Get all pets for logged-in user
    @GetMapping
    public ResponseEntity<?> getMyPets(@RequestHeader("X-User-Id") int userId) {
        try {
            List<Pet> pets = petService.getPetsByOwnerId(userId);
            return ResponseEntity.ok(pets);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch pets: " + e.getMessage()));
        }
    }

    // Get pet by ID
    @GetMapping("/{petId}")
    public ResponseEntity<?> getPetById(@RequestHeader("X-User-Id") int userId, @PathVariable int petId) {
        try {
            Pet pet = petService.getPetById(petId, userId);
            if (pet != null) {
                return ResponseEntity.ok(pet);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Pet not found"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch pet: " + e.getMessage()));
        }
    }

    // Update pet
    @PutMapping("/{petId}")
    public ResponseEntity<?> updatePet(
            @RequestHeader("X-User-Id") int userId,
            @PathVariable int petId,
            @RequestBody PetDTO petDTO) {
        try {
            Pet updatedPet = petService.updatePet(petId, userId, petDTO);
            if (updatedPet != null) {
                return ResponseEntity.ok(Map.of("message", "Pet updated successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Pet not found"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update pet: " + e.getMessage()));
        }
    }

    // Delete pet
    @DeleteMapping("/{petId}")
    public ResponseEntity<?> deletePet(@RequestHeader("X-User-Id") int userId, @PathVariable int petId) {
        try {
            boolean deleted = petService.deletePet(petId, userId);
            if (deleted) {
                return ResponseEntity.ok(Map.of("message", "Pet deleted successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Pet not found"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete pet: " + e.getMessage()));
        }
    }

    // Get pet statistics
    @GetMapping("/stats")
    public ResponseEntity<?> getPetStats(@RequestHeader("X-User-Id") int userId) {
        try {
            PetService.PetStatsDTO stats = petService.getPetStats(userId);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch pet statistics: " + e.getMessage()));
        }
    }
}