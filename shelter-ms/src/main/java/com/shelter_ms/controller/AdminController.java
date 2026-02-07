package com.shelter_ms.controller;

import com.shelter_ms.entity.Profile;
import com.shelter_ms.entity.Register;
import com.shelter_ms.repository.ProfileRepository;
import com.shelter_ms.repository.RegisterRepository;
import org.springframework.core.io.Resource;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/api/admin")

@CrossOrigin(origins = "http://localhost:3000")

public class AdminController {

    @Autowired
    private RegisterRepository registerRepo;

    @Autowired
    private ProfileRepository profileRepo;


    @GetMapping("/pending")
    public List<Register> getPendingShelters() {
        return registerRepo.findByStatus("PENDING");
    }


    @PostMapping("/approve/{id}")
    @Transactional
    public String approveShelter(@PathVariable int id) {

        Register reg = registerRepo.findById(id).orElseThrow();

        long count = profileRepo.count() + 1;
        String regId = String.format("REG-%04d", count);

        Profile profile = new Profile();
        profile.setRegId(regId);


        profile.setShelterName(reg.getShelterName());
        profile.setLicenseNumber(reg.getLicenseNumber());
        profile.setEmail(reg.getEmail());
        profile.setPhone(reg.getPhoneNumber());
        profile.setDescription(reg.getDescription());
        profile.setAddress(reg.getAddress());

        profileRepo.save(profile);

        reg.setStatus("APPROVED");
        registerRepo.save(reg);

        return "Shelter Approved with ID: " + regId;
    }


    @PostMapping("/reject/{id}")
    public String rejectShelter(@PathVariable int id) {
        Register reg = registerRepo.findById(id).orElseThrow();
        reg.setStatus("REJECTED");
        registerRepo.save(reg);
        return "Shelter Rejected";
    }


    @GetMapping("/all")
    public List<Register> getAllShelters() {
        return registerRepo.findAll();
    }

    @GetMapping("/dashboard-stats")
    public Map<String, Long> getDashboardStats() {

        Map<String, Long> stats = new HashMap<>();

        stats.put("totalUsers", 0L); // add later when you have UserRepo
        stats.put("totalPets", 0L);  // add later when you have PetRepo

        stats.put("verifiedShelters", profileRepo.count()); // APPROVED shelters
        stats.put("pendingShelters", registerRepo.countByStatus("PENDING"));

        return stats;
    }



    @GetMapping("/stats")
    public Map<String, Long> getStats() {

        Map<String, Long> stats = new HashMap<>();

        stats.put("pending", registerRepo.countByStatus("PENDING"));
        stats.put("approved", registerRepo.countByStatus("APPROVED"));
        stats.put("flagged", registerRepo.countByStatus("REJECTED"));

        return stats;
    }

    @GetMapping("/shelter/{id}")
    public Register getShelterById(@PathVariable int id) {
        return registerRepo.findById(id).orElseThrow();
    }

    @GetMapping("/document/{id}")
    public ResponseEntity<byte[]> getDocumentByShelter(@PathVariable int id) throws IOException {

        Register reg = registerRepo.findById(id).orElseThrow();

        String baseDir = "C:\\Users\\ADMIN\\Documents\\GitHub\\Petcare-backend\\shelter-ms\\verificationdocu\\";

        Path filePath = Paths.get(baseDir +
                reg.getDocumentPath().replace("verificationdocu\\", "")
                        .replace("verificationdocu/", ""));

        byte[] fileBytes = java.nio.file.Files.readAllBytes(filePath);

        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=\"license.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(fileBytes);
    }











}
