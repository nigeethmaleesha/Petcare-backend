package com.petowner_ms.service;

import com.petowner_ms.data.Pet;
import com.petowner_ms.data.PetRepository;
import com.petowner_ms.data.GrowthRecord;
import com.petowner_ms.data.GrowthRecordRepository;
import com.petowner_ms.dto.GrowthRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GrowthRecordService {

    @Autowired
    private GrowthRecordRepository growthRecordRepository;

    @Autowired
    private PetRepository petRepository;

    // Add growth record
    public GrowthRecord addGrowthRecord(int petId, int petOwnerId, GrowthRecordDTO growthRecordDTO) {
        // Verify pet belongs to owner
        Pet pet = petRepository.findByPetIdAndOwnerId(petId, petOwnerId);
        if (pet == null) {
            return null;
        }

        GrowthRecord growthRecord = new GrowthRecord();
        growthRecord.setPetId(petId);
        growthRecord.setMeasurementDate(growthRecordDTO.getMeasurementDate());
        growthRecord.setWeight(growthRecordDTO.getWeight());
        growthRecord.setHeight(growthRecordDTO.getHeight());
        growthRecord.setNotes(growthRecordDTO.getNotes());

        return growthRecordRepository.save(growthRecord);
    }

    // Get all growth records for a pet
    public List<GrowthRecord> getGrowthRecordsByPetId(int petId, int petOwnerId) {
        // Verify pet belongs to owner
        Pet pet = petRepository.findByPetIdAndOwnerId(petId, petOwnerId);
        if (pet == null) {
            return null;
        }

        return growthRecordRepository.findByPetId(petId);
    }

    // Get latest growth record
    public GrowthRecord getLatestGrowthRecord(int petId, int petOwnerId) {
        // Verify pet belongs to owner
        Pet pet = petRepository.findByPetIdAndOwnerId(petId, petOwnerId);
        if (pet == null) {
            return null;
        }

        return growthRecordRepository.findLatestByPetId(petId);
    }

    // Delete growth record
    public boolean deleteGrowthRecord(int growthId, int petOwnerId) {
        GrowthRecord growthRecord = growthRecordRepository.findById(growthId).orElse(null);
        if (growthRecord == null) {
            return false;
        }

        // Verify pet belongs to owner
        Pet pet = petRepository.findByPetIdAndOwnerId(growthRecord.getPetId(), petOwnerId);
        if (pet == null) {
            return false;
        }

        growthRecordRepository.delete(growthRecord);
        return true;
    }
}