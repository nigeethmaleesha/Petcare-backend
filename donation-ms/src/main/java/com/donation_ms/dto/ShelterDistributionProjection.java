package com.donation_ms.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ShelterDistributionProjection {
    Integer getShelterId();
    String getShelterName();
    BigDecimal getTotalReceived();
    LocalDateTime getLastTransaction();
    Long getCampaignCount();
}