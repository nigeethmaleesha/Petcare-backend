package com.Adoption_ms.services;

import com.Adoption_ms.data.AdoptionRequest;
import com.Adoption_ms.data.AdoptionRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdoptionRequestService implements AdoptionRerService {

    private final AdoptionRequestRepository repository;

    public AdoptionRequestService(AdoptionRequestRepository repository) {
        this.repository = repository;
    }

    private synchronized String generateNextId() {
        List<String> ids = repository.findLastId();

        if (ids.isEmpty() || !ids.get(0).startsWith("REQ-")) {
            return "REQ-001";
        }

        int lastNum = Integer.parseInt(ids.get(0).split("-")[1]);
        return String.format("REQ-%03d", lastNum + 1);
    }

    @Override
    public AdoptionRequest createRequest(AdoptionRequest request) {
        if (request.getRequest_id() == null || request.getRequest_id().isEmpty()) {
            request.setRequest_id(generateNextId());
        }
        return repository.save(request);
    }

    @Override
    public List<AdoptionRequest> getAllRequests() {
        return repository.findAll();
    }

    @Override
    public Optional<AdoptionRequest> getRequestById(String id) {
        return repository.findById(id);
    }

    @Override
    public AdoptionRequest updateRequest(String id, AdoptionRequest updated) {

        AdoptionRequest existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found: " + id));

        existing.setStatus(updated.getStatus());
        existing.setType_of_home(updated.getType_of_home());
        existing.setFenced_yard(updated.getFenced_yard());
        existing.setActivity_level(updated.getActivity_level());
        existing.setHours_alone_per_day(updated.getHours_alone_per_day());
        existing.setFullname(updated.getFullname());
        existing.setContact_no(updated.getContact_no());

        return repository.save(existing);
    }

    @Override
    public void deleteRequest(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<AdoptionRequest> getRequestsByShelterId(String shelterId) {
        return repository.findByShelterId(shelterId);
    }
    public AdoptionRequest updateStatus(String id, String status) {
        AdoptionRequest request = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(status);
        return repository.save(request);
    }
}
