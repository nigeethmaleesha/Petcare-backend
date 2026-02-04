package com.Adoption_ms.services;

import com.Adoption_ms.data.Adoption;
import com.Adoption_ms.data.AdoptionRepository;
import com.Adoption_ms.data.AdoptionRequest;
import com.Adoption_ms.data.AdoptionRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdoptionRequestService {

    @Autowired
    private AdoptionRequestRepository requestRepository;

    @Autowired
    private AdoptionRepository adoptionRepository;

    // Get all adoption requests
    public List<AdoptionRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    // Get adoption request by ID
    public Optional<AdoptionRequest> getRequestById(int id) {
        return requestRepository.findById(id);
    }

    // Create new adoption request
    public AdoptionRequest createRequest(AdoptionRequest request) {
        // Fetch pet_name and shelter_id from Adoption table if adoption_id is provided
        if (request.getAdoption_id() > 0) {
            Optional<Adoption> adoptionOpt = adoptionRepository.findById(request.getAdoption_id());
            if (adoptionOpt.isPresent()) {
                Adoption adoption = adoptionOpt.get();
                request.setPet_name(adoption.getPet_name());
                request.setShelter_id(adoption.getShelter_id());
            } else {
                throw new RuntimeException("Adoption not found with id " + request.getAdoption_id());
            }
        }
        return requestRepository.save(request);
    }

    // Update adoption request
    public AdoptionRequest updateRequest(int id, AdoptionRequest updatedRequest) {
        return requestRepository.findById(id).map(request -> {
            // Allow updating type_of_home, fenced_yard, activity_level, hours_alone_per_day, status
            request.setType_of_home(updatedRequest.getType_of_home());
            request.setFenced_yard(updatedRequest.getFenced_yard());
            request.setActivity_level(updatedRequest.getActivity_level());
            request.setHours_alone_per_day(updatedRequest.getHours_alone_per_day());
            request.setStatus(updatedRequest.getStatus());

            // Optional: Allow updating adoption_id (will also update pet_name and shelter_id)
            if (updatedRequest.getAdoption_id() > 0) {
                request.setAdoption_id(updatedRequest.getAdoption_id());
                Optional<Adoption> adoptionOpt = adoptionRepository.findById(updatedRequest.getAdoption_id());
                if (adoptionOpt.isPresent()) {
                    Adoption adoption = adoptionOpt.get();
                    request.setPet_name(adoption.getPet_name());
                    request.setShelter_id(adoption.getShelter_id());
                }
            }

            return requestRepository.save(request);
        }).orElseThrow(() -> new RuntimeException("Request not found with id " + id));
    }

    // Delete adoption request
    public void deleteRequest(int id) {
        requestRepository.deleteById(id);
    }
    // Get adoption requests by shelter ID
    public List<AdoptionRequest> getRequestsByShelterId(int shelterId) {
        return requestRepository.findByShelterId(shelterId);
    }

}
