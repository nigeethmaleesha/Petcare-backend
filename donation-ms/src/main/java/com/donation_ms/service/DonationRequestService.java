package com.donation_ms.service;

import com.donation_ms.entity.DonationRequest;
import com.donation_ms.entity.DonationRequestStatus;
import com.donation_ms.repository.DonationRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationRequestService {

    private final DonationRequestRepository repository; // Fixed: Changed variable name to match constructor

    public DonationRequestService(DonationRequestRepository repository) {
        this.repository = repository;
    }

    // CREATE
    public DonationRequest create(DonationRequest request) {
        request.setStatus(DonationRequestStatus.OPEN);
        return repository.save(request);
    }

    // READ
    public DonationRequest getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation request not found"));
    }

    public List<DonationRequest> getAll() {
        return repository.findAll();
    }

    public List<DonationRequest> getOpenRequests() {
        return repository.findByStatus(DonationRequestStatus.OPEN);
    }

    // UPDATE
    public DonationRequest update(Integer id, DonationRequest updated) {
        DonationRequest existing = getById(id);

        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setTargetAmount(updated.getTargetAmount());
        existing.setEndDate(updated.getEndDate());

        // Add more fields that can be updated
        if (updated.getShelterId() != null) {
            existing.setShelterId(updated.getShelterId());
        }
        if (updated.getShelterName() != null) {
            existing.setShelterName(updated.getShelterName());
        }
        if (updated.getCurrentAmount() != null) {
            existing.setCurrentAmount(updated.getCurrentAmount());
        }
        if (updated.getStatus() != null) {
            existing.setStatus(updated.getStatus());
        }
        if (updated.getImageUrl() != null) {
            existing.setImageUrl(updated.getImageUrl());
        }
        if (updated.getStartDate() != null) {
            existing.setStartDate(updated.getStartDate());
        }

        return repository.save(existing);
    }

    // CLOSE / CANCEL (No delete – professional rule)
    public void cancel(Integer id) {
        DonationRequest request = getById(id);
        request.setStatus(DonationRequestStatus.CANCELLED);
        repository.save(request);
    }

    // Get donation requests by shelter ID
    public List<DonationRequest> getDonationRequestsByShelter(Integer shelterId) {
        return repository.findByShelterId(shelterId);
    }
}