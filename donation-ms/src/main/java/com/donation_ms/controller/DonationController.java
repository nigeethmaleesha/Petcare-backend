package com.donation_ms.controller;


import com.donation_ms.dto.ShelterDistributionDTO;
import com.donation_ms.entity.Donation;
import com.donation_ms.service.DonationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/donations")
@CrossOrigin(origins = "http://localhost:3000")
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @PostMapping
    public ResponseEntity<Donation> createDonation(@RequestBody Donation donation) {
        Donation saved = donationService.createDonation(donation);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Donation>> getAllDonations() {
        return ResponseEntity.ok(donationService.getAllDonations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donation> getDonationById(@PathVariable Integer id) {
        return ResponseEntity.ok(donationService.getDonationById(id));
    }

    @GetMapping("/request/{donationRequestId}")
    public ResponseEntity<List<Donation>> getDonationsByRequest(@PathVariable Integer donationRequestId) {
        return ResponseEntity.ok(donationService.getDonationsByRequest(donationRequestId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Donation> updateDonation(@PathVariable Integer id, @RequestBody Donation donation) {
        return ResponseEntity.ok(donationService.updateDonation(id, donation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonation(@PathVariable Integer id) {
        donationService.deleteDonation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats/total")
    public BigDecimal getTotalReceived() {
        return donationService.getTotalReceived();
    }

    @GetMapping("/stats/this-month")
    public BigDecimal getTotalThisMonth() {
        return donationService.getTotalReceivedThisMonth();
    }

    @GetMapping("/stats/shelter-distribution")
    public ResponseEntity<List<ShelterDistributionDTO>> getShelterDistribution() {
        try {
            List<ShelterDistributionDTO> distribution = donationService.getShelterDistribution();
            return ResponseEntity.ok(distribution);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/shelter/{shelterId}")
    public ResponseEntity<List<Donation>> getDonationsByShelter(@PathVariable Integer shelterId) {
        try {
            List<Donation> donations = donationService.getDonationsByShelter(shelterId);
            return ResponseEntity.ok(donations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/shelter/{shelterId}/search")
    public ResponseEntity<List<Donation>> getDonationsByShelterWithSearch(
            @PathVariable Integer shelterId,
            @RequestParam(required = false) String query) {
        try {
            List<Donation> donations = donationService.getDonationsByShelterWithSearch(shelterId, query);
            return ResponseEntity.ok(donations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/stats/shelter/{shelterId}/total")
    public ResponseEntity<BigDecimal> getTotalReceivedByShelter(@PathVariable Integer shelterId) {
        try {
            BigDecimal total = donationService.getTotalReceivedByShelter(shelterId);
            return ResponseEntity.ok(total);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/stats/shelter/{shelterId}/this-month")
    public ResponseEntity<BigDecimal> getThisMonthReceivedByShelter(@PathVariable Integer shelterId) {
        try {
            BigDecimal monthlyTotal = donationService.getThisMonthReceivedByShelter(shelterId);
            return ResponseEntity.ok(monthlyTotal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/stats/shelter/{shelterId}/distribution")
    public ResponseEntity<ShelterDistributionDTO> getShelterDistributionById(@PathVariable Integer shelterId) {
        try {
            ShelterDistributionDTO distribution = donationService.getShelterDistributionById(shelterId);
            return ResponseEntity.ok(distribution);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
