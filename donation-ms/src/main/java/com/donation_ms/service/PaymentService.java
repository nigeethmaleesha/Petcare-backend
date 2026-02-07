package com.donation_ms.service;

import com.donation_ms.entity.Payment;
import com.donation_ms.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Integer id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found: " + id));
    }

    public List<Payment> getPaymentsByDonation(Integer donationId) {
        return paymentRepository.findByDonationId(donationId);
    }

    @Transactional
    public void updatePaymentStatus(String providerPaymentId, String status) {
        Payment payment = paymentRepository.findByProviderPaymentId(providerPaymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setProviderStatus(status);
        paymentRepository.save(payment);
    }
}
