package com.donation_ms.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ShelterDistributionDTO {
    private Integer shelterId;
    private String shelterName;
    private BigDecimal totalReceived;
    private LocalDateTime lastTransaction;
    private Integer campaignCount;

    // No-args constructor (REQUIRED)
    public ShelterDistributionDTO() {}

    // All-args constructor (optional but useful)
    public ShelterDistributionDTO(Integer shelterId, String shelterName,
                                  BigDecimal totalReceived, LocalDateTime lastTransaction,
                                  Integer campaignCount) {
        this.shelterId = shelterId;
        this.shelterName = shelterName;
        this.totalReceived = totalReceived;
        this.lastTransaction = lastTransaction;
        this.campaignCount = campaignCount;
    }

    // Getters and Setters (all are required)
    public Integer getShelterId() { return shelterId; }
    public void setShelterId(Integer shelterId) { this.shelterId = shelterId; }

    public String getShelterName() { return shelterName; }
    public void setShelterName(String shelterName) { this.shelterName = shelterName; }

    public BigDecimal getTotalReceived() { return totalReceived; }
    public void setTotalReceived(BigDecimal totalReceived) { this.totalReceived = totalReceived; }

    public LocalDateTime getLastTransaction() { return lastTransaction; }
    public void setLastTransaction(LocalDateTime lastTransaction) { this.lastTransaction = lastTransaction; }

    public Integer getCampaignCount() { return campaignCount; }
    public void setCampaignCount(Integer campaignCount) { this.campaignCount = campaignCount; }
}