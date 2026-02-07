package com.shelter_ms.controller;

import com.shelter_ms.entity.Profile;
import com.shelter_ms.entity.Register;
import com.shelter_ms.repository.ProfileRepository;
import com.shelter_ms.repository.RegisterRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
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
}
