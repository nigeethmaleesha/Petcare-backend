package com.petowner_ms.service;

import com.petowner_ms.data.Pet;
import com.petowner_ms.data.PetRepository;
import com.petowner_ms.data.Vaccination;
import com.petowner_ms.data.VaccinationRepository;
import com.petowner_ms.dto.VaccinationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VaccinationService {

    @Autowired
    private VaccinationRepository vaccinationRepository;

    @Autowired
    private PetRepository petRepository;

    // Add vaccination for a pet
    public Vaccination addVaccination(int petId, int petOwnerId, VaccinationDTO vaccinationDTO) {
        // Verify pet belongs to owner
        Pet pet = petRepository.findByPetIdAndOwnerId(petId, petOwnerId);
        if (pet == null) {
            return null;
        }

        Vaccination vaccination = new Vaccination();
        vaccination.setPetId(petId);
        vaccination.setVaccineName(vaccinationDTO.getVaccineName());
        vaccination.setVaccinationDate(vaccinationDTO.getVaccinationDate());
        vaccination.setClinicName(vaccinationDTO.getClinicName());
        vaccination.setVeterinarianName(vaccinationDTO.getVeterinarianName());
        vaccination.setNotes(vaccinationDTO.getNotes());
        vaccination.setNextDueDate(vaccinationDTO.getNextDueDate());
        vaccination.setReminderSet(vaccinationDTO.isReminderSet());
        vaccination.setStatus(vaccinationDTO.getStatus());

        return vaccinationRepository.save(vaccination);
    }

    // Get all vaccinations for a pet
    public List<Vaccination> getVaccinationsByPetId(int petId, int petOwnerId) {
        // Verify pet belongs to owner
        Pet pet = petRepository.findByPetIdAndOwnerId(petId, petOwnerId);
        if (pet == null) {
            return null;
        }

        return vaccinationRepository.findByPetId(petId);
    }

    // Update vaccination status
    public Vaccination updateVaccinationStatus(int vaccinationId, int petOwnerId, String status) {
        Vaccination vaccination = vaccinationRepository.findById(vaccinationId).orElse(null);
        if (vaccination == null) {
            return null;
        }

        // Verify pet belongs to owner
        Pet pet = petRepository.findByPetIdAndOwnerId(vaccination.getPetId(), petOwnerId);
        if (pet == null) {
            return null;
        }

        vaccination.setStatus(status);
        return vaccinationRepository.save(vaccination);
    }

    // Delete vaccination
    public boolean deleteVaccination(int vaccinationId, int petOwnerId) {
        Vaccination vaccination = vaccinationRepository.findById(vaccinationId).orElse(null);
        if (vaccination == null) {
            return false;
        }

        // Verify pet belongs to owner
        Pet pet = petRepository.findByPetIdAndOwnerId(vaccination.getPetId(), petOwnerId);
        if (pet == null) {
            return false;
        }

        vaccinationRepository.delete(vaccination);
        return true;
    }
}