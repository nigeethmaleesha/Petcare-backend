package com.shelter_ms.controller;

import com.shelter_ms.entity.Profile;
import com.shelter_ms.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Base64;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shelter/profile")
@CrossOrigin
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepo;

    @GetMapping("/email/{email}")
    public ResponseEntity<Profile> getByEmail(@PathVariable String email) {
        return profileRepo.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    //UPDATE BY EMAIL WITH IMAGE
    @PutMapping("/update/email/{email}")
    public ResponseEntity<?> updateProfile(
            @PathVariable String email,
            @RequestParam String streetAddress,
            @RequestParam String addressLine2,
            @RequestParam String city,
            @RequestParam String state,
            @RequestParam String zipCode,
            @RequestParam String country,
            @RequestParam(required = false) MultipartFile profileImage
    ) throws IOException {

        Optional<Profile> optionalProfile = profileRepo.findByEmail(email);

        if (optionalProfile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Profile profile = optionalProfile.get();

        profile.setStreetAddress(streetAddress);
        profile.setAddressLine2(addressLine2);
        profile.setCity(city);
        profile.setState(state);
        profile.setZipCode(zipCode);
        profile.setCountry(country);

        if (profileImage != null && !profileImage.isEmpty()) {
            profile.setProfileImage(profileImage.getBytes());
        }

        profileRepo.save(profile);

        return ResponseEntity.ok("Updated Successfully");
    }


    @GetMapping("/test")
    public List<Profile> test() {
        return profileRepo.findAll();
    }

}



