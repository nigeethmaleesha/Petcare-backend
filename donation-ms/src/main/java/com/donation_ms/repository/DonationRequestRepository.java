package com.donation_ms.repository;


import com.donation_ms.entity.DonationRequest;
import com.donation_ms.entity.DonationRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRequestRepository
        extends JpaRepository<DonationRequest, Integer> {

    List<DonationRequest> findByStatus(DonationRequestStatus status);

    List<DonationRequest> findByShelterId(Integer shelterId);

}