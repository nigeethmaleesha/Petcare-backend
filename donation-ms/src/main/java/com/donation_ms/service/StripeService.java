package com.donation_ms.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.billingportal.Session;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StripeService {

    @Value("${stripe.api.key}")
    private String apiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = apiKey;
    }

    public PaymentIntent createPaymentIntent(long amount) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount) // amount in cents (e.g., 1000 = $10.00)
                .setCurrency("usd")
                .addPaymentMethodType("card")
                .build();

        return PaymentIntent.create(params);
    }

    public String createCheckoutSession(long amount, String title, String purpose) throws StripeException {
        Map<String, Object> params = new HashMap<>();

        List<Object> lineItems = new ArrayList<>();
        Map<String, Object> lineItem = new HashMap<>();
        lineItem.put("price_data", Map.of(
                "currency", "usd",
                "product_data", Map.of("name", title + " - " + purpose),
                "unit_amount", amount
        ));
        lineItem.put("quantity", 1);
        lineItems.add(lineItem);

        params.put("line_items", lineItems);
        params.put("mode", "payment");
        params.put("success_url", "http://localhost:3000/donation-success");
        params.put("cancel_url", "http://localhost:3000/donation-cancel");

        Session session = Session.create(params);
        return session.getId();
    }
}