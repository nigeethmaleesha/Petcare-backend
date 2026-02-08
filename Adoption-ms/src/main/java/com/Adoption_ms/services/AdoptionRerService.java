package com.Adoption_ms.services;

import com.Adoption_ms.data.AdoptionRequest;
import java.util.List;
import java.util.Optional;

public interface AdoptionRerService {
    AdoptionRequest createRequest(AdoptionRequest request);

    List<AdoptionRequest> getAllRequests();

    Optional<AdoptionRequest> getRequestById(String id);

    AdoptionRequest updateRequest(String id, AdoptionRequest request);

    void deleteRequest(String id);

    List<AdoptionRequest> getRequestsByShelterId(String shelterId);
}
