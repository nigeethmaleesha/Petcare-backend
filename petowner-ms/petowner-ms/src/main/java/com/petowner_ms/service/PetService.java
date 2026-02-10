package com.petowner_ms.service;

import com.petowner_ms.data.*;
import com.petowner_ms.dto.PetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private VaccinationRepository vaccinationRepository;

    @Autowired
    private GrowthRecordRepository growthRecordRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    // Add a new pet
    public Pet addPet(int petOwnerId, PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setPetOwnerId(petOwnerId);
        pet.setPetName(petDTO.getPetName());
        pet.setSpecies(petDTO.getSpecies());
        pet.setBreed(petDTO.getBreed());
        pet.setDateOfBirth(petDTO.getDateOfBirth());
        pet.setWeight(petDTO.getWeight());
        pet.setHeight(petDTO.getHeight());
        pet.setHealthNotes(petDTO.getHealthNotes());
        pet.setImageUrl(petDTO.getImageUrl());

        return petRepository.save(pet);
    }

    // Get all pets for a pet owner
    public List<Pet> getPetsByOwnerId(int petOwnerId) {
        return petRepository.findByPetOwnerId(petOwnerId);
    }

    // Get pet by ID (with owner verification)
    public Pet getPetById(int petId, int petOwnerId) {
        return petRepository.findByPetIdAndOwnerId(petId, petOwnerId);
    }

    // Update pet
    public Pet updatePet(int petId, int petOwnerId, PetDTO petDTO) {
        Pet pet = petRepository.findByPetIdAndOwnerId(petId, petOwnerId);
        if (pet == null) {
            return null;
        }

        pet.setPetName(petDTO.getPetName());
        pet.setSpecies(petDTO.getSpecies());
        pet.setBreed(petDTO.getBreed());
        pet.setDateOfBirth(petDTO.getDateOfBirth());
        pet.setWeight(petDTO.getWeight());
        pet.setHeight(petDTO.getHeight());
        pet.setHealthNotes(petDTO.getHealthNotes());
        if (petDTO.getImageUrl() != null) {
            pet.setImageUrl(petDTO.getImageUrl());
        }

        return petRepository.save(pet);
    }

    // Delete pet
    public boolean deletePet(int petId, int petOwnerId) {
        Pet pet = petRepository.findByPetIdAndOwnerId(petId, petOwnerId);
        if (pet != null) {
            petRepository.delete(pet);
            return true;
        }
        return false;
    }

    // Get pet statistics
    public PetStatsDTO getPetStats(int petOwnerId) {
        List<Pet> pets = petRepository.findByPetOwnerId(petOwnerId);
        PetStatsDTO stats = new PetStatsDTO();
        stats.setTotalPets(pets.size());

        // Calculate upcoming and overdue vaccinations
        int upcomingCount = 0;
        int overdueCount = 0;

        for (Pet pet : pets) {
            List<Vaccination> vaccinations = vaccinationRepository.findByPetId(pet.getPetId());
            for (Vaccination v : vaccinations) {
                if ("Due Soon".equals(v.getStatus())) {
                    upcomingCount++;
                } else if ("Overdue".equals(v.getStatus())) {
                    overdueCount++;
                }
            }
        }

        stats.setUpcomingVaccinations(upcomingCount);
        stats.setOverdueVaccinations(overdueCount);

        return stats;
    }

    // DTO for pet statistics
    public static class PetStatsDTO {
        private int totalPets;
        private int upcomingVaccinations;
        private int overdueVaccinations;

        // Getters and Setters
        public int getTotalPets() { return totalPets; }
        public void setTotalPets(int totalPets) { this.totalPets = totalPets; }

        public int getUpcomingVaccinations() { return upcomingVaccinations; }
        public void setUpcomingVaccinations(int upcomingVaccinations) { this.upcomingVaccinations = upcomingVaccinations; }

        public int getOverdueVaccinations() { return overdueVaccinations; }
        public void setOverdueVaccinations(int overdueVaccinations) { this.overdueVaccinations = overdueVaccinations; }
    }
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }
}