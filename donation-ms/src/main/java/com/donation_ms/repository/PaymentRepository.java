package com.donation_ms.repository;

import com.donation_ms.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    List<Payment> findByDonationId(Integer donationId);

    Optional<Payment> findByProviderPaymentId(String providerPaymentId);
}
