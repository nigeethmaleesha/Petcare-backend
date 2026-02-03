package com.Adoption_ms.controller;

import com.Adoption_ms.data.AdoptionRequest;
import com.Adoption_ms.services.AdoptionRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adoption-requests")
public class AdoptionRequestController {

    @Autowired
    private AdoptionRequestService service;

    // Get all adoption requests
    @GetMapping
    public List<AdoptionRequest> getAllRequests() {
        return service.getAllRequests();
    }

    // Get adoption request by ID
    @GetMapping("/{id}")
    public ResponseEntity<AdoptionRequest> getRequestById(@PathVariable int id) {
        return service.getRequestById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create new adoption request
    @PostMapping
    public AdoptionRequest createRequest(@RequestBody AdoptionRequest request) {
        return service.createRequest(request);
    }

    // Update adoption request
    @PutMapping("/{id}")
    public AdoptionRequest updateRequest(@PathVariable int id, @RequestBody AdoptionRequest request) {
        return service.updateRequest(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteRequest(@PathVariable int id) {
        AdoptionRequest request = service.getRequestById(id)
                .orElseThrow(() -> new RuntimeException("Adoption Request ID not found: " + id));

        service.deleteRequest(id);
        return "Adoption Request Deleted Successfully";
    }
    // Get adoption requests by shelter ID
    @GetMapping("/shelter/{shelterId}")
    public List<AdoptionRequest> getRequestsByShelterId(@PathVariable int shelterId) {
        return service.getRequestsByShelterId(shelterId);
    }


}
