package com.donation_ms.service;


import com.donation_ms.dto.ShelterDistributionDTO;
import com.donation_ms.dto.ShelterDistributionProjection;
import com.donation_ms.entity.Donation;
import com.donation_ms.entity.DonationStatus;
import com.donation_ms.repository.DonationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DonationService {

    private final DonationRepository donationRepository;


    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;

    }

    @Transactional
    public Donation createDonation(Donation donation) {
        donation.setStatus(DonationStatus.PENDING);
        return donationRepository.save(donation);
    }

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    public Donation getDonationById(Integer id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found with id: " + id));
    }

    public List<Donation> getDonationsByRequest(Integer donationRequestId) {
        return donationRepository.findByDonationRequestId(donationRequestId);
    }

    @Transactional
    public Donation updateDonation(Integer id, Donation updatedDonation) {
        Donation existing = getDonationById(id);

        existing.setDonorName(updatedDonation.getDonorName());
        existing.setDonorEmail(updatedDonation.getDonorEmail());
        existing.setAmount(updatedDonation.getAmount());
        existing.setStatus(updatedDonation.getStatus());

        return donationRepository.save(existing);
    }

    @Transactional
    public void deleteDonation(Integer id) {
        Donation donation = getDonationById(id);
        if (donation.getStatus() == DonationStatus.SUCCESS) {
            throw new RuntimeException("Cannot delete a successful donation");
        }
        donationRepository.delete(donation);
    }

    public BigDecimal getTotalReceived() {
        return donationRepository.findAll().stream()
                .filter(d -> d.getStatus() == DonationStatus.SUCCESS)
                .map(Donation::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalReceivedThisMonth() {
        LocalDate now = LocalDate.now();
        return donationRepository.findAll().stream()
                .filter(d -> d.getStatus() == DonationStatus.SUCCESS)
                .filter(d -> d.getCreatedAt().getMonth() == now.getMonth() &&
                        d.getCreatedAt().getYear() == now.getYear())
                .map(Donation::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }



    // ADD THIS NEW METHOD to save donation from payment
    @Transactional
    public Donation saveDonationFromPayment(Integer donationRequestId, BigDecimal amount,
                                            String purpose, String donorName, String donorEmail,
                                            String transactionId) {
        Donation donation = new Donation();

        // Use the actual donation request ID passed from frontend
        donation.setDonationRequestId(donationRequestId);
        donation.setDonorName(donorName != null ? donorName : "Anonymous Donor");
        donation.setDonorEmail(donorEmail);
        donation.setAmount(amount);
        donation.setPurpose(purpose);
        donation.setStatus(DonationStatus.SUCCESS); // Mark as SUCCESS immediately

        System.out.println("💾 Saving donation to donations table:");
        System.out.println("  - Donation Request ID: " + donationRequestId);
        System.out.println("  - Amount: $" + amount);
        System.out.println("  - Purpose: " + purpose);
        System.out.println("  - Donor: " + donation.getDonorName());
        System.out.println("  - Transaction ID: " + transactionId);

        return donationRepository.save(donation);
    }

    // Keep the old method for backward compatibility (or remove it)
    @Transactional
    public Donation saveDonationFromPayment(String transactionId, BigDecimal amount,
                                            String purpose, String donorName, String donorEmail) {
        // This is the OLD method - kept for backward compatibility
        return saveDonationFromPayment(
                generateDonationRequestId(transactionId), // Use generated ID
                amount, purpose, donorName, donorEmail, transactionId
        );
    }

    private Integer generateDonationRequestId(String transactionId) {
        // Fallback method - only use if no request ID provided
        return Math.abs(transactionId.hashCode()) % 10000;
    }
    public List<ShelterDistributionDTO> getShelterDistribution() {
        List<ShelterDistributionProjection> projections = donationRepository.getShelterDistribution();

        return projections.stream()
                .map(projection -> {
                    ShelterDistributionDTO dto = new ShelterDistributionDTO();
                    dto.setShelterId(projection.getShelterId());
                    dto.setShelterName(projection.getShelterName());
                    dto.setTotalReceived(projection.getTotalReceived());
                    dto.setLastTransaction(projection.getLastTransaction());

                    Long campaignCount = projection.getCampaignCount();
                    dto.setCampaignCount(campaignCount != null ? campaignCount.intValue() : 0);

                    return dto;
                })
                .collect(Collectors.toList());
    }

    //new
    // Add these methods to your existing DonationService.java

    public List<Donation> getDonationsByShelter(Integer shelterId) {
        return donationRepository.findByShelterId(shelterId);
    }

    public List<Donation> getDonationsByShelterWithSearch(Integer shelterId, String search) {
        return donationRepository.findByShelterIdWithSearch(shelterId, search);
    }

    public BigDecimal getTotalReceivedByShelter(Integer shelterId) {
        return donationRepository.getTotalReceivedByShelter(shelterId);
    }

    public BigDecimal getThisMonthReceivedByShelter(Integer shelterId) {
        return donationRepository.getThisMonthReceivedByShelter(shelterId);
    }

    // Get shelter distribution for a specific shelter only
    public ShelterDistributionDTO getShelterDistributionById(Integer shelterId) {
        List<ShelterDistributionProjection> projections = donationRepository.getShelterDistribution();

        return projections.stream()
                .filter(projection -> projection.getShelterId().equals(shelterId))
                .findFirst()
                .map(projection -> {
                    ShelterDistributionDTO dto = new ShelterDistributionDTO();
                    dto.setShelterId(projection.getShelterId());
                    dto.setShelterName(projection.getShelterName());
                    dto.setTotalReceived(projection.getTotalReceived());
                    dto.setLastTransaction(projection.getLastTransaction());

                    Long campaignCount = projection.getCampaignCount();
                    dto.setCampaignCount(campaignCount != null ? campaignCount.intValue() : 0);

                    return dto;
                })
                .orElseGet(() -> {
                    // Return empty DTO if shelter not found
                    ShelterDistributionDTO dto = new ShelterDistributionDTO();
                    dto.setShelterId(shelterId);
                    dto.setShelterName("Unknown Shelter");
                    dto.setTotalReceived(BigDecimal.ZERO);
                    dto.setLastTransaction(null);
                    dto.setCampaignCount(0);
                    return dto;
                });
    }
}
