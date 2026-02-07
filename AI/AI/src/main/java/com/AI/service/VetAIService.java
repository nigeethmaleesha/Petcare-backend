package com.AI.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class VetAIService {
    @Value("${groq.api.key}")
    private String API_KEY;

    @Value("${groq.api.url}")
    private String API_URL;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getVetResponse(String userMessage) {

        String systemPrompt = """
You are a professional veterinary emergency assistant.

Rules:
1. Always give:
   - Possible cause
   - Immediate home care
   - When to go to vet
2. Ask what animal and age if not given.
3. Never give medicine dosage.
4. Keep answers short and clear.

Format strictly like:

Possible Cause:
- ...

Immediate Care:
- ...

Go to Vet Immediately if:
- ...
""";

        Map<String, Object> body = Map.of(
                "model", "llama-3.3-70b-versatile",
                "messages", List.of(
                        Map.of("role", "system", "content", systemPrompt),
                        Map.of("role", "user", "content", userMessage)
                ),
                "temperature", 0.2
        );


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        Map response = restTemplate.postForObject(API_URL, entity, Map.class);

        List<Map<String,Object>> choices = (List<Map<String,Object>>) response.get("choices");
        Map message = (Map) choices.get(0).get("message");

        return message.get("content").toString();
    }

}
