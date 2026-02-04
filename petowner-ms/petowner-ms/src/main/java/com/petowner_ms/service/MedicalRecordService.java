package com.petowner_ms.service;

import com.petowner_ms.data.Pet;
import com.petowner_ms.data.PetRepository;
import com.petowner_ms.data.MedicalRecord;
import com.petowner_ms.data.MedicalRecordRepository;
import com.petowner_ms.dto.MedicalRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PetRepository petRepository;

    // Add medical record
    public MedicalRecord addMedicalRecord(int petId, int petOwnerId, MedicalRecordDTO medicalRecordDTO) {
        // Verify pet belongs to owner
        Pet pet = petRepository.findByPetIdAndOwnerId(petId, petOwnerId);
        if (pet == null) {
            return null;
        }

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setPetId(petId);
        medicalRecord.setRecordDate(medicalRecordDTO.getRecordDate());
        medicalRecord.setConditionName(medicalRecordDTO.getConditionName());
        medicalRecord.setRecordType(medicalRecordDTO.getRecordType());
        medicalRecord.setTreatment(medicalRecordDTO.getTreatment());
        medicalRecord.setNotes(medicalRecordDTO.getNotes());

        return medicalRecordRepository.save(medicalRecord);
    }

    // Get all medical records for a pet
    public List<MedicalRecord> getMedicalRecordsByPetId(int petId, int petOwnerId) {
        // Verify pet belongs to owner
        Pet pet = petRepository.findByPetIdAndOwnerId(petId, petOwnerId);
        if (pet == null) {
            return null;
        }

        return medicalRecordRepository.findByPetId(petId);
    }

    // Search medical records by condition
    public List<MedicalRecord> searchMedicalRecords(int petId, int petOwnerId, String condition) {
        // Verify pet belongs to owner
        Pet pet = petRepository.findByPetIdAndOwnerId(petId, petOwnerId);
        if (pet == null) {
            return null;
        }

        return medicalRecordRepository.searchByCondition(petId, condition);
    }

    // Get medical records by type
    public List<MedicalRecord> getMedicalRecordsByType(int petId, int petOwnerId, String recordType) {
        // Verify pet belongs to owner
        Pet pet = petRepository.findByPetIdAndOwnerId(petId, petOwnerId);
        if (pet == null) {
            return null;
        }

        return medicalRecordRepository.findByPetIdAndType(petId, recordType);
    }

    // Delete medical record
    public boolean deleteMedicalRecord(int medicalId, int petOwnerId) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(medicalId).orElse(null);
        if (medicalRecord == null) {
            return false;
        }

        // Verify pet belongs to owner
        Pet pet = petRepository.findByPetIdAndOwnerId(medicalRecord.getPetId(), petOwnerId);
        if (pet == null) {
            return false;
        }

        medicalRecordRepository.delete(medicalRecord);
        return true;
    }
}