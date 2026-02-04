package com.petowner_ms.controller;

import com.petowner_ms.data.PetOwner;
import com.petowner_ms.dto.RegisterRequest;
import com.petowner_ms.dto.LoginRequest;
import com.petowner_ms.dto.LoginResponse;
import com.petowner_ms.service.PetOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/petowners")
public class PetOwnerController {

    @Autowired
    private PetOwnerService petOwnerService;

    // Register a new pet owner
    @PostMapping("/register")
    public ResponseEntity<?> registerPetOwner(@RequestBody RegisterRequest request) {
        try {
            // Validation
            if (!request.getPassword().equals(request.getConfirmPassword())) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Passwords do not match"));
            }

            PetOwner petOwner = petOwnerService.registerPetOwner(request);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Registration successful");
            response.put("userId", petOwner.getId());
            response.put("fullName", petOwner.getFullName());
            response.put("email", petOwner.getEmail());
            response.put("role", petOwner.getRole());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Registration failed"));
        }
    }

    // Login pet owner
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Optional<PetOwner> petOwnerOpt = petOwnerService.login(request.getEmail(), request.getPassword());

            if (petOwnerOpt.isPresent()) {
                PetOwner petOwner = petOwnerOpt.get();
                LoginResponse response = new LoginResponse(
                        "Login successful",
                        petOwner.getId(),
                        petOwner.getFullName(),
                        petOwner.getEmail(),
                        petOwner.getRole()
                );
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid email or password"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Login failed"));
        }
    }

    // Get pet owner by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPetOwnerById(@PathVariable int id) {
        try {
            Optional<PetOwner> petOwner = petOwnerService.getPetOwnerById(id);

            if (petOwner.isPresent()) {
                return ResponseEntity.ok(petOwner.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Pet owner not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch pet owner"));
        }
    }
}