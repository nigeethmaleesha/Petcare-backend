package com.AI.controller;
import com.AI.service.VetAIService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:3000")
public class VetAIController {

    private final VetAIService vetAIService;

    public VetAIController(VetAIService vetAIService) {
        this.vetAIService = vetAIService;
    }

    @PostMapping("/emergency-chat")
    public Map<String, Object> chat(@RequestBody Map<String, Object> request) {

        List<Map<String,String>> messages =
                (List<Map<String,String>>) request.get("messages");

        String userMessage = messages.get(messages.size() - 1).get("content");

        String reply = vetAIService.getVetResponse(userMessage);

        return Map.of(
                "success", true,
                "text", reply
        );
    }
}
