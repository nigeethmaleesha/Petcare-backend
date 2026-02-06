package com.donation_ms.controller;

import com.donation_ms.entity.DonationRequest;
import com.donation_ms.service.DonationRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donation-requests")
@CrossOrigin(origins = "http://localhost:3000")
public class DonationRequestController {

    private final DonationRequestService service;

    public DonationRequestController(DonationRequestService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public DonationRequest create(@RequestBody DonationRequest request) {
        return service.create(request);
    }

    // READ ALL
    @GetMapping
    public List<DonationRequest> getAll() {
        return service.getAll();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public DonationRequest getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    // READ OPEN ONLY
    @GetMapping("/open")
    public List<DonationRequest> getOpenRequests() {
        return service.getOpenRequests();
    }

    // UPDATE
    @PutMapping("/{id}")
    public DonationRequest update(
            @PathVariable Integer id,
            @RequestBody DonationRequest request) {
        return service.update(id, request);
    }

    // CANCEL (SOFT DELETE)
    @DeleteMapping("/{id}")
    public void cancel(@PathVariable Integer id) {
        service.cancel(id);
    }

    // GET BY SHELTER ID
    @GetMapping("/shelter/{shelterId}")
    public ResponseEntity<List<DonationRequest>> getDonationRequestsByShelter(@PathVariable Integer shelterId) {
        try {
            List<DonationRequest> requests = service.getDonationRequestsByShelter(shelterId);
            return ResponseEntity.ok(requests);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}