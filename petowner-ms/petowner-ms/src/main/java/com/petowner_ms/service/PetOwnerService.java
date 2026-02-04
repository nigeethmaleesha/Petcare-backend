package com.petowner_ms.service;

import com.petowner_ms.data.PetOwner;
import com.petowner_ms.data.PetOwnerRepository;
import com.petowner_ms.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PetOwnerService {

    @Autowired
    private PetOwnerRepository petOwnerRepository;

    // Register a new pet owner
    public PetOwner registerPetOwner(RegisterRequest request) {
        // Check if email already exists
        if (petOwnerRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Create new pet owner
        PetOwner petOwner = new PetOwner();
        petOwner.setFullName(request.getFullName());
        petOwner.setEmail(request.getEmail());
        petOwner.setPassword(request.getPassword());
        petOwner.setAgree(request.isAgree());

        return petOwnerRepository.save(petOwner);
    }

    // Login pet owner
    public Optional<PetOwner> login(String email, String password) {
        return petOwnerRepository.findByEmailAndPassword(email, password);
    }

    // Get pet owner by ID
    public Optional<PetOwner> getPetOwnerById(int id) {
        return petOwnerRepository.findById(id);
    }
}