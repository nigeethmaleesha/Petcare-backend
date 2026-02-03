package com.shelter_ms.controller;

import com.shelter_ms.entity.Profile;
import com.shelter_ms.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shelter/profile")
@CrossOrigin
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepo;

    // GET BY EMAIL
    @GetMapping("/email/{email}")
    public Profile getByEmail(@PathVariable String email) {
        return profileRepo.findByEmail(email);
    }

    // UPDATE BY EMAIL
    @PutMapping("/update/email/{email}")
    public Profile updateByEmail(@PathVariable String email,
                                 @RequestBody Profile updated) {

        Profile profile = profileRepo.findByEmail(email);

        profile.setAddress(updated.getAddress());
        profile.setProfileImage(updated.getProfileImage());

        return profileRepo.save(profile);
    }
}


