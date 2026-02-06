package com.AI_Assistance_ms.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OpenAIService {

    private final ChatClient chatClient;
    private final MockOpenAIService mockService;

    @Value("${spring.ai.openai.api-key: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}")
    private String apiKey;

    @Value("${spring.ai.openai.chat.options.model:gpt-4-turbo-preview}")
    private String model;

    public OpenAIService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
        this.mockService = new MockOpenAIService();
    }

    public String getEmergencyPetAdvice(String userMessage) {
        // If no API key or mock mode, use mock service
        if ("mock".equals(apiKey) || apiKey == null || apiKey.isEmpty() || apiKey.equals("your-api-key-here")) {
            return mockService.getMockAdvice(userMessage);
        }

        try {
            // Original OpenAI implementation
            String systemPrompt = """
                You are an AI emergency veterinary assistant for a pet care website called "PetCare Emergency".
                Your role is to provide immediate, helpful advice for pet emergencies while emphasizing when professional veterinary care is necessary.
                
                IMPORTANT RULES:
                1. You are ONLY for pet (animal) healthcare assistance
                2. Do NOT answer questions unrelated to pet health
                3. Focus on dogs, cats, and common household pets
                4. Always prioritize safety and recommend vet visits when in doubt
                5. Format responses in a clear, structured way with bullet points
                6. Identify emergency situations clearly with URGENT labels
                
                FORMAT YOUR RESPONSE WITH:
                - Assessment of the situation
                - Immediate first-aid steps (if applicable)
                - Home care instructions (when safe)
                - Clear indicators for when to seek veterinary help
                - Estimated urgency level (Low/Medium/High)
                
                User's concern: {userMessage}
                
                Remember: You are NOT a replacement for a veterinarian, but a helpful assistant.
                """;

            PromptTemplate promptTemplate = new PromptTemplate(systemPrompt);
            Prompt prompt = promptTemplate.create();

            ChatResponse response = chatClient.prompt(prompt)
                    .user(userMessage)
                    .call()
                    .chatResponse();

            return response.getResult().getOutput().getText();
        } catch (Exception e) {
            // Fallback to mock service if OpenAI fails
            System.out.println("OpenAI API error, using mock service: " + e.getMessage());
            return mockService.getMockAdvice(userMessage);
        }
    }

    public String analyzePetImage(String imageBase64, String description) {
        // Always use mock for image analysis for now
        return mockService.analyzePetImageMock(description);
    }

    public boolean isEmergencySituation(String aiResponse) {
        String lowerResponse = aiResponse.toLowerCase();
        return lowerResponse.contains("urgent") ||
                lowerResponse.contains("emergency") ||
                lowerResponse.contains("immediately") ||
                lowerResponse.contains("vet now") ||
                lowerResponse.contains("critical") ||
                lowerResponse.contains("life-threatening") ||
                lowerResponse.contains("🚨") ||
                lowerResponse.contains("go to emergency");
    }

    // Complete MockOpenAIService with ALL methods
    private static class MockOpenAIService {
        public String getMockAdvice(String userMessage) {
            String lowerMessage = userMessage.toLowerCase();

            if (lowerMessage.contains("bleed") || lowerMessage.contains("blood")) {
                return getBleedingResponse(userMessage);
            } else if (lowerMessage.contains("vomit") || lowerMessage.contains("throw up")) {
                return getVomitingResponse(userMessage);
            } else if (lowerMessage.contains("breath") || lowerMessage.contains("choke") || lowerMessage.contains("cough")) {
                return getBreathingResponse(userMessage);
            } else if (lowerMessage.contains("injure") || lowerMessage.contains("hurt") || lowerMessage.contains("wound") || lowerMessage.contains("limping")) {
                return getInjuryResponse(userMessage);
            } else if (lowerMessage.contains("seizure") || lowerMessage.contains("convulsion") || lowerMessage.contains("shake") || lowerMessage.contains("tremor")) {
                return getSeizureResponse(userMessage);
            } else if (lowerMessage.contains("poison") || lowerMessage.contains("ate") || lowerMessage.contains("toxic") || lowerMessage.contains("chemical")) {
                return getPoisonResponse(userMessage);
            } else if (lowerMessage.contains("diarrhea") || lowerMessage.contains("loose stool")) {
                return getDiarrheaResponse(userMessage);
            } else if (lowerMessage.contains("eye") || lowerMessage.contains("vision")) {
                return getEyeResponse(userMessage);
            } else if (lowerMessage.contains("skin") || lowerMessage.contains("rash") || lowerMessage.contains("itch")) {
                return getSkinResponse(userMessage);
            } else {
                return getGeneralResponse(userMessage);
            }
        }

        private String getBleedingResponse(String userMessage) {
            return """
                🩸 **BLEEDING EMERGENCY ASSESSMENT**
                
                Based on your description: "%s"
                
                **IMMEDIATE ACTION:**
                1. Apply direct pressure with clean cloth
                2. Elevate wound if possible
                3. Do NOT remove soaked cloth - add more layers
                
                **EMERGENCY SIGNS:**
                • Bleeding doesn't stop after 10 minutes
                • Blood spurting or pooling
                • Pale/white gums
                • Weakness or collapse
                
                **URGENCY: HIGH** - Seek veterinary care immediately.
                """.formatted(userMessage);
        }

        private String getVomitingResponse(String userMessage) {
            return """
                🤢 **VOMITING/DIGESTIVE ISSUES**
                
                Based on your description: "%s"
                
                **IMMEDIATE CARE:**
                1. Withhold food 4-6 hours (water available)
                2. Offer small water amounts frequently
                3. Bland diet after fasting
                
                **EMERGENCY IF:**
                • Vomiting >3 times in 24 hours
                • Blood in vomit
                • Lethargy or weakness
                • Suspected toxin ingestion
                
                **URGENCY: MEDIUM** - Contact vet if persists >24 hours.
                """.formatted(userMessage);
        }

        private String getBreathingResponse(String userMessage) {
            return """
                😮‍💨 **BREATHING DIFFICULTIES - EMERGENCY**
                
                Based on your description: "%s"
                
                **🚨 THIS IS URGENT 🚨**
                
                **IMMEDIATE ACTION:**
                1. Keep pet calm
                2. Loosen collar
                3. Cool, ventilated area
                
                **EMERGENCY SIGNS:**
                • Blue/purple gums
                • Noisy breathing
                • Gasping/choking
                • Collapse
                
                **URGENCY: CRITICAL** - Go to emergency vet NOW.
                """.formatted(userMessage);
        }

        // MISSING METHOD: getInjuryResponse
        private String getInjuryResponse(String userMessage) {
            return """
                🩹 **INJURY ASSESSMENT**
                
                Based on your description: "%s"
                
                **IMMEDIATE CARE:**
                1. Keep pet still
                2. Check for fractures
                3. Control bleeding if present
                
                **DO NOT:**
                • Give human pain meds
                • Attempt to set fractures
                • Force movement
                
                **EMERGENCY IF:**
                • Visible bone/deep wound
                • Unable to bear weight
                • Signs of pain/distress
                
                **URGENCY: MEDIUM-HIGH** - Seek vet care for any significant injury.
                """.formatted(userMessage);
        }

        // MISSING METHOD: getSeizureResponse
        private String getSeizureResponse(String userMessage) {
            return """
                ⚡ **SEIZURE EMERGENCY**
                
                Based on your description: "%s"
                
                **IMMEDIATE SAFETY:**
                1. Clear area around pet
                2. DO NOT restrain or put anything in mouth
                3. Time the seizure
                4. Cushion head if possible
                
                **EMERGENCY IF:**
                • Seizure lasts >5 minutes
                • Multiple seizures in 24 hours
                • First seizure in pet over 5 years
                • Difficulty breathing
                
                **POST-SEIZURE CARE:**
                • Quiet, dark room
                • Offer water when alert
                • Monitor breathing
                
                **URGENCY: CRITICAL** - All seizures require veterinary evaluation.
                """.formatted(userMessage);
        }

        // MISSING METHOD: getPoisonResponse
        private String getPoisonResponse(String userMessage) {
            return """
                ☠️ **SUSPECTED POISONING - EMERGENCY**
                
                Based on your description: "%s"
                
                **🚨 IMMEDIATE ACTION REQUIRED 🚨**
                
                **DO NOT WAIT FOR SYMPTOMS**
                
                **STEP 1: CONTACT**
                📞 Animal Poison Control: (888) 426-4435
                📞 Emergency Vet: Go immediately
                
                **COMMON TOXINS:**
                • Human medications
                • Chocolate, grapes, xylitol
                • Antifreeze, rodenticides
                • Lilies (cats), sago palm
                
                **DO NOT:**
                • Induce vomiting unless instructed
                • Give any home remedies
                • Wait for symptoms to appear
                
                **URGENCY: CRITICAL** - Poisoning requires immediate veterinary intervention.
                """.formatted(userMessage);
        }

        private String getDiarrheaResponse(String userMessage) {
            return """
                💩 **DIARRHEA/GASTROINTESTINAL DISTRESS**
                
                Based on your description: "%s"
                
                **IMMEDIATE CARE:**
                1. Ensure fresh water available
                2. Fast for 12 hours, then bland diet
                3. Monitor frequency/consistency
                
                **EMERGENCY IF:**
                • Blood in stool
                • >5 episodes in 24 hours
                • Accompanied by vomiting
                • Lethargy/weakness
                
                **HOME TREATMENT:**
                • Bland diet: Boiled chicken + rice
                • Pumpkin: Plain canned pumpkin
                • Probiotics: Pet-specific
                
                **URGENCY: LOW-MEDIUM** - Unless with other symptoms.
                """.formatted(userMessage);
        }

        private String getEyeResponse(String userMessage) {
            return """
                👁️ **EYE INJURY/INFECTION**
                
                Based on your description: "%s"
                
                **🚨 EYE EMERGENCIES REQUIRE URGENT CARE 🚨**
                
                **IMMEDIATE ACTION:**
                1. Apply E-collar immediately
                2. DO NOT rub/touch eye
                3. Protect eye with damp cloth if open wound
                
                **EMERGENCY CONDITIONS:**
                • Eye popped out (proptosis)
                • Corneal ulcers
                • Glaucoma
                • Chemical exposure
                
                **SIGNS OF EMERGENCY:**
                • Sudden blindness
                • Eye protruding
                • Penetrating injury
                • Severe swelling
                
                **URGENCY: HIGH** - Eye conditions can lead to permanent vision loss.
                """.formatted(userMessage);
        }

        private String getSkinResponse(String userMessage) {
            return """
                🩹 **SKIN CONDITIONS & ALLERGIES**
                
                Based on your description: "%s"
                
                **IMMEDIATE CARE:**
                1. Apply E-collar to prevent scratching
                2. Cool bath with oatmeal shampoo
                3. Cold compress on affected areas
                
                **EMERGENCY IF:**
                • Severe swelling (face/throat)
                • Difficulty breathing
                • Large/deep wounds
                • Burns or snake bites
                
                **HOME CARE:**
                • Keep area clean and dry
                • Use vet-approved products only
                • Prevent licking/chewing
                
                **DO NOT USE:**
                • Human creams/ointments
                • Essential oils
                • Home remedies without vet advice
                
                **URGENCY: LOW-MEDIUM** - Unless breathing affected.
                """.formatted(userMessage);
        }

        private String getGeneralResponse(String userMessage) {
            return """
                🐾 **GENERAL PET HEALTH ASSESSMENT**
                
                Based on your description: "%s"
                
                **VITAL SIGNS TO MONITOR:**
                • Temperature: 100.5°F - 102.5°F
                • Heart Rate: 70-160 (dogs), 140-220 (cats)
                • Respiration: 15-30 (dogs), 20-30 (cats)
                • Gum Color: Pink (not pale/white/blue)
                
                **HOME OBSERVATION:**
                1. Appetite/Thirst: Normal, increased, decreased
                2. Energy Level: Normal, lethargic
                3. Behavior Changes: Document specifics
                4. Elimination: Frequency, consistency
                
                **WHEN TO SEE VET:**
                • Symptoms worsen despite home care
                • Multiple symptoms present
                • Senior/young animals
                • No improvement in 24-48 hours
                
                **URGENCY LEVEL: LOW** - Based on general description.
                """.formatted(userMessage);
        }

        public String analyzePetImageMock(String description) {
            return """
                📸 **IMAGE ANALYSIS REPORT**
                
                Based on your description: "%s"
                
                **VISUAL ASSESSMENT:**
                • Photo received for analysis
                • Limited AI analysis available without Vision API
                
                **RECOMMENDED ACTIONS:**
                1. Please describe visible symptoms in detail
                2. Note any injuries, swelling, or abnormalities
                3. Mention color changes or unusual spots
                
                **FOR BETTER ANALYSIS:**
                - Take clear, well-lit photos
                - Include multiple angles if possible
                - Show affected area clearly
                
                **EMERGENCY SIGNS IN PHOTOS:**
                ✅ Visible wounds or bleeding
                ✅ Swelling or inflammation
                ✅ Eye/nose discharge
                ✅ Skin lesions or rashes
                
                **Note:** This is a basic analysis. For accurate diagnosis, consult a veterinarian.
                """.formatted(description);
        }
    }
}