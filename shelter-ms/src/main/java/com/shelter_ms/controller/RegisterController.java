package com.shelter_ms.controller;

import com.shelter_ms.entity.Register;
import com.shelter_ms.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/shelter")
public class RegisterController {

    @Autowired
    private RegisterRepository registerRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;


    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> registerShelter(
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("shelterName") String shelterName,
            @RequestParam("licenseNumber") String licenseNumber,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("address") String address,
            @RequestParam("description") String description,
            @RequestParam("document") MultipartFile document
    ) {
        try {

            String uploadDir = "verificationdocu/";
            Files.createDirectories(Paths.get(uploadDir));

            String filePath = uploadDir + document.getOriginalFilename();
            Path path = Paths.get(filePath);
            Files.write(path, document.getBytes());



            // ✅ Save data to DB
            Register reg = new Register();
            reg.setFullName(fullName);
            reg.setEmail(email);
            reg.setPassword(encoder.encode(password));
            reg.setShelterName(shelterName);
            reg.setLicenseNumber(licenseNumber);
            reg.setPhoneNumber(phoneNumber);
            reg.setAddress(address);
            reg.setDescription(description);
            reg.setDocumentPath(filePath);
            reg.setStatus("PENDING");

            registerRepo.save(reg);

            return ResponseEntity.ok("Registered Successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}
