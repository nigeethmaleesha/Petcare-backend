package com.Adoption_ms.controller;

import com.Adoption_ms.data.AdoptionRequest;
import com.Adoption_ms.services.AdoptionRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adoption-requests")
@CrossOrigin
public class AdoptionRequestController {

    private final AdoptionRequestService service;

    public AdoptionRequestController(AdoptionRequestService service) {
        this.service = service;
    }

    @GetMapping
    public List<AdoptionRequest> getAllRequests() {
        return service.getAllRequests();
    }

    @GetMapping("/{id}")
    public AdoptionRequest getRequestById(@PathVariable String id) {
        return service.getRequestById(id)
                .orElseThrow(() -> new RuntimeException("Request not found: " + id));
    }

    @PostMapping
    public AdoptionRequest createRequest(@RequestBody AdoptionRequest request) {
        return service.createRequest(request);
    }

    @PutMapping("/{id}")
    public AdoptionRequest updateRequest(
            @PathVariable String id,
            @RequestBody AdoptionRequest request) {
        return service.updateRequest(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteRequest(@PathVariable String id) {
        service.deleteRequest(id);
        return "Adoption Request Deleted Successfully";
    }

    @GetMapping("/shelter/{shelterId}")
    public List<AdoptionRequest> getRequestsByShelterId(@PathVariable String shelterId) {
        return service.getRequestsByShelterId(shelterId);
    }
    @PutMapping("/{id}/status")
    public AdoptionRequest updateStatus(
            @PathVariable String id,
            @RequestBody java.util.Map<String, String> payload) {
        String newStatus = payload.get("status");
        return service.updateStatus(id, newStatus); // You will need to add this method to your Service
    }
}
