package com.AI_Assistance_ms.controller;

import com.AI_Assistance_ms.dto.ChatRequest;
import com.AI_Assistance_ms.dto.ChatResponse;
import com.AI_Assistance_ms.dto.EmergencyTipsResponse;
import com.AI_Assistance_ms.service.EmergencyChatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "${cors.allowed-origins:http://localhost:3000}")
public class EmergencyAIController {

    private final EmergencyChatService emergencyChatService;

    @Value("${server.port:8082}")
    private String serverPort;

    public EmergencyAIController(EmergencyChatService emergencyChatService) {
        this.emergencyChatService = emergencyChatService;
    }

    @PostMapping("/emergency-chat")
    public ResponseEntity<ChatResponse> emergencyChat(@RequestBody ChatRequest request) {
        ChatResponse response = emergencyChatService.processEmergencyChat(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/extract-tips")
    public ResponseEntity<EmergencyTipsResponse> extractTips(@RequestBody ChatResponse aiResponse) {
        if (aiResponse == null || aiResponse.getText() == null || aiResponse.getText().isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new EmergencyTipsResponse(false, "No AI response provided")
            );
        }

        EmergencyTipsResponse tips = emergencyChatService.extractEmergencyTips(aiResponse.getText());
        return ResponseEntity.ok(tips);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("PetCare Emergency AI Assistant is running on port: " + serverPort);
    }

    @GetMapping("/status")
    public ResponseEntity<ChatResponse> status() {
        ChatResponse response = new ChatResponse(
                true,
                "✅ Emergency Veterinary AI Assistant is online and ready to help your pets!"
        );
        return ResponseEntity.ok(response);
    }
}