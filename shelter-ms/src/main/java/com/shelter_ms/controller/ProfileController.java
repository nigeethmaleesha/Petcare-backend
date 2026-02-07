package com.shelter_ms.controller;

import com.shelter_ms.entity.Profile;
import com.shelter_ms.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    // UPDATE BY EMAIL
    @PutMapping("/update/email/{email}")
    public Profile updateByEmail(@PathVariable String email,
                                 @RequestBody Profile updated) {

        Profile profile = profileRepo.findByEmail(email).orElse(null);
        if (profile == null) return null;

        profile.setStreetAddress(updated.getStreetAddress());
        profile.setAddressLine2(updated.getAddressLine2());
        profile.setCity(updated.getCity());
        profile.setState(updated.getState());
        profile.setZipCode(updated.getZipCode());
        profile.setCountry(updated.getCountry());
        profile.setProfileImage(updated.getProfileImage());

        return profileRepo.save(profile);
    }

    @GetMapping("/test")
    public List<Profile> test() {
        return profileRepo.findAll();
    }

}



