package com.donation_ms.service;

import com.donation_ms.dto.ShelterDistributionDTO;
import com.donation_ms.dto.ShelterDistributionProjection;
import com.donation_ms.entity.Donation;
import com.donation_ms.entity.DonationRequest;
import com.donation_ms.entity.DonationStatus;
import com.donation_ms.repository.DonationRepository;
import com.donation_ms.repository.DonationRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final DonationRequestRepository donationRequestRepository;

    public DonationService(DonationRepository donationRepository,
                           DonationRequestRepository donationRequestRepository) {
        this.donationRepository = donationRepository;
        this.donationRequestRepository = donationRequestRepository;
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

    @Transactional
    public Donation saveDonationFromPayment(Integer donationRequestId, BigDecimal amount,
                                            String purpose, String donorName, String donorEmail,
                                            String transactionId) {
        // Get the donation request to get shelter information
        DonationRequest donationRequest = donationRequestRepository.findById(donationRequestId)
                .orElseThrow(() -> new RuntimeException("Donation request not found with id: " + donationRequestId));

        Donation donation = new Donation();
        donation.setDonationRequestId(donationRequestId);
        donation.setShelterId(donationRequest.getShelterId());
        donation.setDonorName(donorName != null ? donorName : "Anonymous Donor");
        donation.setDonorEmail(donorEmail);
        donation.setAmount(amount);
        donation.setStatus(DonationStatus.SUCCESS);

        System.out.println("💾 Saving donation to donations table:");
        System.out.println("  - Donation Request ID: " + donationRequestId);
        System.out.println("  - Shelter ID: " + donationRequest.getShelterId());
        System.out.println("  - Amount: $" + amount);
        System.out.println("  - Donor: " + donation.getDonorName());
        System.out.println("  - Transaction ID: " + transactionId);

        return donationRepository.save(donation);
    }

    @Transactional
    public void updateDonationRequestAmount(Integer donationRequestId, BigDecimal amount) {
        DonationRequest donationRequest = donationRequestRepository.findById(donationRequestId)
                .orElseThrow(() -> new RuntimeException("Donation request not found"));

        BigDecimal newCurrentAmount = donationRequest.getCurrentAmount().add(amount);
        donationRequest.setCurrentAmount(newCurrentAmount);

        // Check if target is reached
        if (newCurrentAmount.compareTo(donationRequest.getTargetAmount()) >= 0) {
            donationRequest.setStatus(com.donation_ms.entity.DonationRequestStatus.COMPLETED);
        }

        donationRequestRepository.save(donationRequest);
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

    // Add these missing methods:

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

    public ShelterDistributionDTO getShelterDistributionById(Integer shelterId) {
        List<ShelterDistributionDTO> allDistributions = getShelterDistribution();

        return allDistributions.stream()
                .filter(distribution -> distribution.getShelterId().equals(shelterId))
                .findFirst()
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

    // Keep the old method for backward compatibility (or remove it)
    @Transactional
    public Donation saveDonationFromPayment(String transactionId, BigDecimal amount,
                                            String purpose, String donorName, String donorEmail) {
        // This is the OLD method - kept for backward compatibility
        return saveDonationFromPayment(
                generateDonationRequestId(transactionId),
                amount, purpose, donorName, donorEmail, transactionId
        );
    }

    private Integer generateDonationRequestId(String transactionId) {
        // Fallback method - only use if no request ID provided
        return Math.abs(transactionId.hashCode()) % 10000;
    }
}