package com.donation_ms.controller;

import com.donation_ms.service.StripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stripe")
public class StripeCheckoutController {

    private final StripeService stripeService;

    public StripeCheckoutController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<Map<String, String>> createCheckoutSession(
            @RequestBody Map<String, Object> data) {
        try {
            long amount = Long.parseLong(data.get("amount").toString());
            String title = data.get("title").toString();
            String purpose = data.get("purpose").toString();

            String sessionId = stripeService.createCheckoutSession(
                    amount * 100, // Convert to cents
                    title,
                    purpose
            );

            Map<String, String> response = new HashMap<>();
            response.put("sessionId", sessionId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
