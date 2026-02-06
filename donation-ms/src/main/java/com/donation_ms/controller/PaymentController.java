package com.donation_ms.controller;

import com.donation_ms.entity.Donation;
import com.donation_ms.entity.Payment;
import com.donation_ms.entity.PaymentProvider;
import com.donation_ms.service.DonationService;
import com.donation_ms.service.PaymentService;
import com.donation_ms.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    private final PaymentService paymentService;
    private final StripeService stripeService;
    private final DonationService donationService;

    public PaymentController(PaymentService paymentService, StripeService stripeService, DonationService donationService) {
        this.paymentService = paymentService;
        this.stripeService = stripeService;
        this.donationService = donationService;
    }

    // CREATE payment (manual for now)
    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        return new ResponseEntity<>(
                paymentService.createPayment(payment),
                HttpStatus.CREATED
        );
    }

    // GET all payments
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Integer id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    // GET by donation
    @GetMapping("/donation/{donationId}")
    public ResponseEntity<List<Payment>> getPaymentsByDonation(
            @PathVariable Integer donationId) {
        return ResponseEntity.ok(
                paymentService.getPaymentsByDonation(donationId)
        );
    }

    @PostMapping("/create-payment-intent")
    public ResponseEntity<Map<String, String>> createPaymentIntent(@RequestBody Map<String, Object> data) {
        try {
            // Safe extraction of numbers
            long amount = Long.parseLong(data.get("amount").toString());
            Integer donationRequestId = Integer.parseInt(data.get("donationRequestId").toString()); // CHANGED: donationRequestId
            String purpose = data.get("purpose").toString();
            String title = data.get("title").toString();

            System.out.println("🎯 Creating payment intent for:");
            System.out.println("  - Donation Request ID: " + donationRequestId);
            System.out.println("  - Amount: $" + (amount / 100.0));
            System.out.println("  - Purpose: " + purpose);

            // 1. Create Stripe PaymentIntent
            PaymentIntent paymentIntent = stripeService.createPaymentIntent(amount);

            // 2. Convert cents to dollars (BigDecimal)
            BigDecimal amountInDollars = BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(100));

            // 3. Save to PAYMENTS table
            Payment payment = new Payment();
            payment.setDonationId(donationRequestId); // Store request ID temporarily
            payment.setProvider(PaymentProvider.valueOf("STRIPE"));
            payment.setProviderPaymentId(paymentIntent.getId());
            payment.setProviderStatus(paymentIntent.getStatus());
            payment.setAmount(amountInDollars);
            payment.setCurrency(paymentIntent.getCurrency());

            Payment savedPayment = paymentService.createPayment(payment);
            System.out.println("💳 Payment record created with ID: " + savedPayment.getId());

            // 4. Return clientSecret for Frontend Elements
            Map<String, String> response = new HashMap<>();
            response.put("clientSecret", paymentIntent.getClientSecret());
            response.put("paymentIntentId", paymentIntent.getId());
            response.put("donationRequestId", donationRequestId.toString()); // Return for reference
            return ResponseEntity.ok(response);

        } catch (StripeException e) {
            System.err.println("❌ Stripe error: " + e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            System.err.println("❌ General error: " + e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to create payment intent: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // ADD THIS NEW ENDPOINT to handle successful payments
    @PostMapping("/payment-success")
    public ResponseEntity<Map<String, Object>> handlePaymentSuccess(@RequestBody Map<String, Object> data) {
        try {
            String paymentIntentId = data.get("paymentIntentId").toString();
            Integer donationRequestId = Integer.parseInt(data.get("donationRequestId").toString()); // IMPORTANT
            BigDecimal amount = new BigDecimal(data.get("amount").toString());
            String purpose = data.get("purpose").toString();
            String donorName = data.containsKey("donorName") ?
                    data.get("donorName").toString() : "Anonymous Donor";
            String donorEmail = data.containsKey("donorEmail") ?
                    data.get("donorEmail").toString() : null;

            System.out.println("✅ Processing payment success:");
            System.out.println("  - Payment Intent ID: " + paymentIntentId);
            System.out.println("  - Donation Request ID: " + donationRequestId);
            System.out.println("  - Amount: $" + amount);
            System.out.println("  - Donor: " + donorName);

            // 1. Update payment status in payments table
            paymentService.updatePaymentStatus(paymentIntentId, "succeeded");

            // 2. Save to DONATIONS table using DonationService
            Donation savedDonation = donationService.saveDonationFromPayment(
                    donationRequestId, // Pass the actual request ID
                    amount,
                    purpose,
                    donorName,
                    donorEmail,
                    paymentIntentId
            );

            System.out.println("💾 Donation saved with ID: " + savedDonation.getId());
            System.out.println("  - Linked to Request ID: " + savedDonation.getDonationRequestId());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Payment processed and donation saved");
            response.put("donationId", savedDonation.getId());
            response.put("donationRequestId", savedDonation.getDonationRequestId());
            response.put("donation", savedDonation);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("❌ Payment success error: " + e.getMessage());
            e.printStackTrace();
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

}
