package com.AI_Assistance_ms.controller;

import com.AI_Assistance_ms.dto.ChatResponse;
import com.AI_Assistance_ms.dto.ImageAnalysisRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "${cors.allowed-origins:http://localhost:3000}")
public class ImageAnalysisController {

    @PostMapping("/analyze-image")
    public ResponseEntity<ChatResponse> analyzeImage(@RequestBody ImageAnalysisRequest request) {
        try {
            // Simple image analysis mock
            String analysis = analyzePetImage(request.getImageBase64(), request.getDescription());

            ChatResponse response = new ChatResponse(true, analysis);
            response.setSessionId(request.getSessionId());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.ok(new ChatResponse(false,
                    "Unable to analyze image. Please describe the issue in text."));
        }
    }

    private String analyzePetImage(String imageBase64, String description) {
        // Mock analysis - in real implementation, use OpenAI Vision API
        return """
            📸 **IMAGE ANALYSIS REPORT**
            
            Based on your image description: "%s"
            
            **Visual Assessment:**
            • Photo received successfully
            • Limited AI analysis available without Vision API
            
            **Recommended Actions:**
            1. Please describe what you see in detail
            2. Note any visible injuries, swelling, or abnormalities
            3. Mention color changes or unusual spots
            
            **For Better Analysis:**
            - Take clear, well-lit photos
            - Include multiple angles if possible
            - Show affected area clearly
            
            **Note:** This is a basic analysis. For accurate diagnosis, consult a veterinarian.
            
            **Emergency signs in photos:**
            ✅ Visible wounds or bleeding
            ✅ Swelling or inflammation
            ✅ Eye/nose discharge
            ✅ Skin lesions or rashes
            """.formatted(description);
    }
}