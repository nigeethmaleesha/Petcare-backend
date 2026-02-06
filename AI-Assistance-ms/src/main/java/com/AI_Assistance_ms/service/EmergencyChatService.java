package com.AI_Assistance_ms.service;

import com.AI_Assistance_ms.data.ChatHistory;
import com.AI_Assistance_ms.data.ChatHistoryRepository;
import com.AI_Assistance_ms.dto.ChatRequest;
import com.AI_Assistance_ms.dto.ChatResponse;
import com.AI_Assistance_ms.dto.EmergencyTipsResponse;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmergencyChatService {

    private final ChatHistoryRepository chatHistoryRepository;
    private final MockAIService mockAIService;

    public EmergencyChatService(ChatHistoryRepository chatHistoryRepository) {
        this.chatHistoryRepository = chatHistoryRepository;
        this.mockAIService = new MockAIService();
    }

    public ChatResponse processEmergencyChat(ChatRequest request) {
        try {
            // Extract the latest user message
            String userMessage = extractLatestUserMessage(request.getMessages());

            if (!isPetRelated(userMessage)) {
                return new ChatResponse(false, null,
                        "I can only assist with pet healthcare emergencies. Please describe your pet's symptoms.");
            }

            // Generate session ID if not provided
            String sessionId = request.getSessionId();
            if (sessionId == null || sessionId.isEmpty()) {
                sessionId = UUID.randomUUID().toString();
            }

            // Get AI response - USE MOCK
            String aiResponse = mockAIService.getEmergencyPetAdvice(userMessage);

            // Check if emergency
            boolean isEmergency = mockAIService.isEmergencySituation(aiResponse);

            // Save to database
            ChatHistory chatHistory = new ChatHistory(
                    sessionId,
                    userMessage,
                    aiResponse,
                    extractPetSymptom(userMessage)
            );
            chatHistory.setEmergency(isEmergency);
            chatHistoryRepository.save(chatHistory);

            ChatResponse response = new ChatResponse(true, aiResponse);
            response.setSessionId(sessionId);
            response.setEmergency(isEmergency);

            return response;

        } catch (Exception e) {
            e.printStackTrace();
            // Use mock as fallback
            return mockAIService.getMockResponse("Error occurred, providing general advice.");
        }
    }

    // Mock AI Service with ALL response methods
    private static class MockAIService {

        public String getEmergencyPetAdvice(String userMessage) {
            String lowerMessage = userMessage.toLowerCase();

            // Emergency templates based on symptoms
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
                
                **IMMEDIATE ACTION REQUIRED:**
                1. Apply direct pressure to the wound with a clean cloth or gauze
                2. Maintain pressure for 5-10 minutes without lifting to check
                3. If possible, elevate the injured area above heart level
                4. Do NOT use tourniquets unless absolutely necessary
                
                **WOUND CLASSIFICATION:**
                • Minor: Superficial cut, minimal bleeding
                • Moderate: Steady bleeding, may need stitches
                • Severe: Heavy bleeding, blood pooling/spurting
                
                **EMERGENCY SIGNS (Go to Vet NOW):**
                ✅ Bleeding doesn't stop after 10 minutes of pressure
                ✅ Blood soaking through multiple bandages quickly
                ✅ Pale or white gums (sign of blood loss)
                ✅ Weakness, collapse, or difficulty breathing
                ✅ Bleeding from nose, mouth, or rectum
                
                **TEMPORARY CARE:**
                • Keep your pet calm and still
                • If minor: Clean with saline solution, apply pet-safe antiseptic
                • Apply Elizabethan collar to prevent licking
                • Monitor for signs of infection (redness, swelling, pus)
                
                **URGENCY LEVEL: HIGH** - Any significant bleeding requires veterinary attention.
                
                **Next Steps:**
                1. For severe bleeding: Go to emergency vet immediately
                2. For moderate bleeding: Call your vet for guidance
                3. For minor bleeding: Clean and monitor, see vet if worsens
                """.formatted(userMessage);
        }

        private String getVomitingResponse(String userMessage) {
            return """
                🤢 **VOMITING/DIGESTIVE ISSUES ASSESSMENT**
                
                Based on your description: "%s"
                
                **IMMEDIATE CARE PROTOCOL:**
                1. **Food Fasting:** Withhold all food for 4-6 hours (water remains available)
                2. **Hydration:** Offer small amounts of water every 30 minutes
                3. **Bland Diet:** After fasting, offer: boiled chicken + white rice (3:1 ratio)
                4. **Monitor:** Count vomiting episodes, note color/content
                
                **CAUSE ANALYSIS:**
                • Dietary indiscretion (ate something unusual)
                • Viral/bacterial infection
                • Parasites
                • Organ disease (kidney/liver)
                • Obstruction (requires immediate vet)
                
                **RED FLAGS (Emergency Vet):**
                ✅ Vomiting more than 3 times in 24 hours
                ✅ Blood in vomit (red, coffee-ground appearance)
                ✅ Projectile vomiting
                ✅ Accompanied by diarrhea
                ✅ Lethargy, weakness, or collapse
                ✅ Suspected ingestion of toxins
                
                **HOME MONITORING CHECKLIST:**
                • Gum color: Should be pink (not pale/white)
                • Skin tent test: Check for dehydration
                • Energy level: Note any changes
                • Appetite: Willingness to eat/drink
                
                **URGENCY LEVEL: MEDIUM-HIGH** - Depends on frequency and accompanying symptoms.
                
                **Timeline for Veterinary Care:**
                • Within 2 hours: If blood present or severe lethargy
                • Within 12 hours: If vomiting continues despite fasting
                • Within 24 hours: If symptoms persist but pet is stable
                """.formatted(userMessage);
        }

        private String getBreathingResponse(String userMessage) {
            return """
                😮‍💨 **BREATHING DIFFICULTIES - POTENTIAL EMERGENCY**
                
                Based on your description: "%s"
                
                **🚨 THIS IS URGENT - DO NOT DELAY 🚨**
                
                **IMMEDIATE LIFESAVING MEASURES:**
                1. **Stay Calm:** Your anxiety can worsen your pet's distress
                2. **Clear Airways:** Check for foreign objects in mouth/throat
                3. **Positioning:** Keep in sternal position (chest down)
                4. **Environment:** Cool, well-ventilated, quiet space
                5. **DO NOT:** Restrict breathing with tight collars/harnesses
                
                **NORMAL BREATHING PARAMETERS:**
                • Dogs: 10-30 breaths per minute (resting)
                • Cats: 20-30 breaths per minute (resting)
                • Count breaths for 15 seconds × 4 = breaths/minute
                
                **EMERGENCY BREATHING PATTERNS:**
                ✅ Open-mouth breathing (cats especially)
                ✅ Abdominal breathing (belly moving excessively)
                ✅ Noisy breathing (wheezing, gurgling, snoring)
                ✅ Extended neck while breathing
                ✅ Blue/purple gums or tongue
                
                **POTENTIAL CAUSES:**
                • Heart failure
                • Pulmonary edema (fluid in lungs)
                • Asthma (common in cats)
                • Tracheal collapse
                • Pneumonia
                • Foreign body obstruction
                
                **WHAT TO DO NOW:**
                1. **Call emergency vet:** Inform them you're coming
                2. **Minimize stress:** Keep other pets/children away
                3. **Prepare for transport:** Use carrier, not loose in car
                4. **Bring medical records:** If available
                
                **URGENCY LEVEL: CRITICAL** - Breathing difficulties are life-threatening emergencies.
                """.formatted(userMessage);
        }

        private String getInjuryResponse(String userMessage) {
            return """
                🩹 **INJURY ASSESSMENT & FIRST AID**
                
                Based on your description: "%s"
                
                **IMMEDIATE ASSESSMENT:**
                1. **Scene Safety:** Ensure you and pet are safe from further harm
                2. **ABC Check:** Airway, Breathing, Circulation
                3. **Level of Consciousness:** Alert, responsive, unresponsive
                4. **Pain Assessment:** Vocalization, guarding, aggression
                
                **DO NOT ATTEMPT:**
                ❌ Do NOT give human pain medications (toxic to pets)
                ❌ Do NOT attempt to set fractures
                ❌ Do NOT force movement if pain is evident
                ❌ Do NOT clean deep wounds with hydrogen peroxide
                
                **INJURY TYPES & RESPONSE:**
                
                **Fractures/Suspected Broken Bones:**
                • Keep pet immobilized
                • Use makeshift splint if trained
                • Transport on rigid surface
                
                **Soft Tissue Injuries (Sprains/Strains):**
                • Restrict activity immediately
                • Apply cold compress 10-15 minutes
                • Elevate if possible
                
                **Wounds/Lacerations:**
                • Control bleeding first
                • Clean with saline if minor
                • Cover with clean dressing
                
                **Head/Spinal Injuries:**
                • IMMOBILIZE completely
                • Use board for transport
                • Monitor breathing closely
                
                **EMERGENCY TRANSPORT PROTOCOL:**
                1. **Stabilize:** Before moving, stabilize injury sites
                2. **Carrier:** Use rigid carrier, not blankets
                3. **Companion:** Have someone monitor during transport
                4. **Call Ahead:** Alert emergency vet of situation
                
                **URGENCY LEVEL: MEDIUM-HIGH** - All significant injuries require veterinary evaluation.
                
                **Monitoring Checklist En Route:**
                • Breathing rate/pattern
                • Gum color (should be pink)
                • Response to stimuli
                • Pain signs
                """.formatted(userMessage);
        }

        private String getSeizureResponse(String userMessage) {
            return """
                ⚡ **SEIZURE EMERGENCY - ACTION REQUIRED**
                
                Based on your description: "%s"
                
                **🚨 SEIZURE IN PROGRESS - DO NOT PANIC 🚨**
                
                **IMMEDIATE SAFETY MEASURES:**
                1. **Clear Area:** Move furniture/objects away from pet
                2. **DO NOT Restrain:** Let seizure run its course
                3. **DO NOT Put Anything in Mouth:** Risk of choking/biting
                4. **Time It:** Note start time and duration
                5. **Cushion Head:** Place soft towel under head if possible
                
                **SEIZURE PHASES:**
                
                **Pre-Ictal (Aura):**
                • Restlessness, anxiety
                • Whining, hiding
                • Salivation
                
                **Ictal (Active Seizure):**
                • Collapse, loss of consciousness
                • Muscle rigidity/convulsions
                • Paddling motions
                • Urination/defecation
                • Jaw chomping
                
                **Post-Ictal (Recovery):**
                • Disorientation, confusion
                • Temporary blindness
                • Excessive thirst/hunger
                • Fatigue, deep sleep
                
                **EMERGENCY CRITERIA (Go to Vet NOW):**
                ✅ Seizure lasts longer than 5 minutes
                ✅ Multiple seizures within 24 hours
                ✅ Cluster seizures (one after another)
                ✅ First-time seizure in pet over 5 years old
                ✅ Difficulty breathing during/after seizure
                ✅ Injury occurred during seizure
                
                **POST-SEIZURE CARE:**
                1. Keep in quiet, dark room
                2. Offer water when fully alert
                3. Monitor breathing and consciousness
                4. Prevent access to stairs/height
                
                **POTENTIAL CAUSES:**
                • Epilepsy (idiopathic)
                • Toxin ingestion
                • Metabolic disorders
                • Brain tumors
                • Infections
                
                **URGENCY LEVEL: CRITICAL** - All seizures require veterinary evaluation.
                
                **What to Tell Your Vet:**
                • Seizure duration
                • Description of movements
                • Behavior before/after
                • Any known toxins/medications
                """.formatted(userMessage);
        }

        private String getPoisonResponse(String userMessage) {
            return """
                ☠️ **SUSPECTED POISONING - EMERGENCY PROTOCOL**
                
                Based on your description: "%s"
                
                **🚨 IMMEDIATE ACTION REQUIRED 🚨**
                
                **DO NOT WAIT FOR SYMPTOMS - POISONING CAN BE FATAL**
                
                **STEP 1: IDENTIFY THE TOXIN**
                • What substance was ingested?
                • How much was ingested?
                • When did ingestion occur?
                • What's the product name/brand?
                
                **STEP 2: EMERGENCY CONTACTS**
                📞 **Animal Poison Control:** (888) 426-4435
                📞 **Your Veterinarian:** Call immediately
                📞 **Emergency Vet:** Locate nearest 24-hour facility
                
                **COMMON TOXINS & SYMPTOMS:**
                
                **Human Medications:**
                • NSAIDs (ibuprofen, naproxen) → Kidney failure
                • Acetaminophen (Tylenol) → Liver failure
                • ADHD/antidepressants → Seizures
                
                **Foods:**
                • Chocolate (theobromine) → Vomiting, seizures
                • Grapes/Raisins → Kidney failure
                • Xylitol (sugar-free) → Hypoglycemia, liver failure
                • Onions/Garlic → Anemia
                
                **Household Items:**
                • Antifreeze (ethylene glycol) → Kidney failure
                • Rodenticides → Internal bleeding
                • Insecticides → Neurological symptoms
                • Cleaning products → Burns, respiratory
                
                **Plants:**
                • Lilies (cats) → Kidney failure
                • Sago Palm → Liver failure
                • Azaleas/Rhododendrons → Cardiac issues
                
                **DO NOT INDUCE VOMITING UNLESS INSTRUCTED:**
                • Some toxins cause more damage coming back up
                • Never induce if pet is unconscious
                • Only with veterinary guidance
                
                **WHAT TO BRING TO VET:**
                • Toxin container/packaging
                • Sample of vomit if present
                • Product label/ingredients
                • Estimate of amount ingested
                
                **URGENCY LEVEL: CRITICAL** - Poisoning requires immediate veterinary intervention.
                
                **Transport Protocol:**
                1. Secure toxin container
                2. Bring pet in carrier
                3. Have someone monitor during transport
                4. Call ahead to alert emergency vet
                """.formatted(userMessage);
        }

        private String getDiarrheaResponse(String userMessage) {
            return """
                💩 **DIARRHEA/GASTROINTESTINAL DISTRESS**
                
                Based on your description: "%s"
                
                **IMMEDIATE CARE PROTOCOL:**
                1. **Hydration Priority:** Ensure fresh water available at all times
                2. **Dietary Management:** Fast for 12 hours, then bland diet
                3. **Monitor Output:** Note frequency, consistency, color
                4. **Sanitation:** Clean area thoroughly to prevent reinfection
                
                **CONSISTENCY SCALE:**
                • Normal: Firm, formed, easy to pick up
                • Soft Serve: Soft but formed
                • Cow Pie: Pourable, loses shape
                • Watery: Liquid, no form
                • Bloody/Mucousy: Emergency indicator
                
                **COLOR INDICATORS:**
                • Brown: Normal
                • Black/Tarry: Digested blood (upper GI)
                • Bright Red: Fresh blood (lower GI)
                • Yellow/Orange: Liver/gallbladder issues
                • Green: Rapid transit, bile
                • Gray/White: Pancreas/liver issues
                
                **EMERGENCY CRITERIA:**
                ✅ Blood in stool (any amount)
                ✅ More than 5 episodes in 24 hours
                ✅ Accompanied by vomiting
                ✅ Lethargy, weakness, collapse
                ✅ Signs of pain/distress
                ✅ Puppy/kitten or senior pet
                
                **HOME TREATMENT:**
                • Bland diet: Boiled chicken + white rice (3:1 ratio)
                • Probiotics: Pet-specific probiotic supplements
                • Pumpkin: Plain canned pumpkin (not pie filling)
                • Electrolytes: Unflavored pediatric electrolyte solution
                
                **POTENTIAL CAUSES:**
                • Dietary indiscretion
                • Parasites (worms, giardia)
                • Bacterial/viral infection
                • Inflammatory bowel disease
                • Food allergies/intolerance
                
                **URGENCY LEVEL: LOW-MEDIUM** - Unless accompanied by other symptoms.
                
                **When to See Vet:**
                • Immediately: If blood present or severe lethargy
                • Within 12 hours: If diarrhea persists despite fasting
                • Within 24 hours: For monitoring and parasite check
                """.formatted(userMessage);
        }

        private String getEyeResponse(String userMessage) {
            return """
                👁️ **EYE INJURY/INFECTION ASSESSMENT**
                
                Based on your description: "%s"
                
                **🚨 EYE EMERGENCIES REQUIRE URGENT CARE 🚨**
                
                **IMMEDIATE ACTION:**
                1. **DO NOT RUB/TOUCH:** Prevent self-trauma
                2. **E-Collar:** Apply Elizabethan collar immediately
                3. **Protect Eye:** If open wound, cover with damp cloth
                4. **DO NOT Use:** Human eye drops/medications
                
                **EMERGENCY EYE CONDITIONS:**
                
                **Proptosis (Eye Popped Out):**
                • Keep eye moist with saline
                • DO NOT attempt to replace
                • Emergency surgery required
                
                **Corneal Ulcer:**
                • Squinting, excessive blinking
                • Cloudiness on surface
                • Redness, discharge
                • Requires medication
                
                **Glaucoma:**
                • Dilated, fixed pupil
                • Cloudy cornea
                • Eye appears enlarged
                • Painful, requires emergency care
                
                **Uveitis (Inflammation):**
                • Red, painful eye
                • Constricted pupil
                • Cloudiness within eye
                • Often indicates systemic disease
                
                **SIGNS OF EYE EMERGENCY:**
                ✅ Sudden blindness or vision loss
                ✅ Eye protruding from socket
                ✅ Penetrating injury (stick, claw)
                ✅ Chemical exposure
                ✅ Severe swelling/inability to open eye
                
                **HOME CARE (Temporary):**
                • Saline rinse: If debris visible
                • Cold compress: For swelling (5 minutes on/off)
                • E-collar: Essential to prevent rubbing
                • Dark, quiet room: Minimize light sensitivity
                
                **URGENCY LEVEL: HIGH** - Eye conditions can lead to permanent vision loss.
                
                **Transport Instructions:**
                1. Apply E-collar before transport
                2. Keep in dark carrier
                3. Have someone monitor
                4. Call ahead to emergency vet
                """.formatted(userMessage);
        }

        private String getSkinResponse(String userMessage) {
            return """
                🩹 **SKIN CONDITIONS & ALLERGIES**
                
                Based on your description: "%s"
                
                **ASSESSMENT & IMMEDIATE CARE:**
                
                **Itching/Scratching:**
                1. **E-Collar:** Prevent self-trauma immediately
                2. **Cool Bath:** Oatmeal shampoo for temporary relief
                3. **Cold Compress:** On affected areas 10-15 minutes
                4. **Antihistamines:** Only if previously prescribed by vet
                
                **Hot Spots (Acute Moist Dermatitis):**
                • Clip hair around area
                • Clean with chlorhexidine solution
                • Apply vet-prescribed topical
                • Keep area dry
                
                **Rashes/Redness:**
                • Identify potential allergens
                • Remove suspected triggers
                • Note any new foods/environments
                • Photograph for vet reference
                
                **Lumps/Bumps:**
                • Measure size (use coin for reference)
                • Note location and characteristics
                • Monitor for growth/changes
                • DO NOT attempt to drain/pop
                
                **EMERGENCY SKIN CONDITIONS:**
                ✅ Severe swelling (face, throat)
                ✅ Difficulty breathing with skin issues
                ✅ Large, deep wounds
                ✅ Burns (chemical/thermal)
                ✅ Suspected snake/spider bites
                ✅ Skin sloughing/peeling
                
                **ALLERGY MANAGEMENT:**
                • Food trials: 8-12 weeks minimum
                • Environmental control: HEPA filters, frequent cleaning
                • Flea control: Essential for flea allergy dermatitis
                • Medication: Only under veterinary supervision
                
                **HOME CARE DO'S & DON'TS:**
                ✅ DO keep area clean and dry
                ✅ DO use vet-approved products only
                ✅ DO prevent licking/chewing
                ❌ DON'T use human creams/ointments
                ❌ DON'T shave without veterinary guidance
                ❌ DON'T apply essential oils
                
                **URGENCY LEVEL: LOW-MEDIUM** - Unless accompanied by breathing difficulties.
                
                **When to See Vet:**
                • Emergency: If swelling affects breathing
                • Urgent: If infection suspected (pus, fever)
                • Soon: For diagnosis and treatment plan
                • Routine: For chronic condition management
                """.formatted(userMessage);
        }

        private String getGeneralResponse(String userMessage) {
            return """
                🐾 **GENERAL PET HEALTH ASSESSMENT**
                
                Based on your description: "%s"
                
                **COMPREHENSIVE HEALTH CHECKLIST:**
                
                **VITAL SIGNS MONITORING:**
                • **Temperature:** 100.5°F - 102.5°F (38°C - 39.2°C)
                • **Heart Rate:** 70-160 (dogs), 140-220 (cats) beats/minute
                • **Respiration:** 15-30 (dogs), 20-30 (cats) breaths/minute
                • **Gum Color:** Pink (not pale/white/blue)
                • **Capillary Refill:** <2 seconds (press gum, color should return quickly)
                
                **HOME OBSERVATION PROTOCOL:**
                1. **Appetite/Thirst:** Normal, increased, decreased, absent
                2. **Energy Level:** Normal, lethargic, hyperactive
                3. **Behavior Changes:** Hiding, aggression, anxiety
                4. **Elimination:** Frequency, consistency, color, effort
                5. **Movement:** Limping, stiffness, difficulty rising
                
                **COMMON CONCERNS & RESPONSES:**
                
                **Not Eating:**
                • Temporary (24h): Monitor hydration
                • Extended (>48h): Veterinary consultation
                • Senior pets: Earlier intervention recommended
                
                **Lethargy:**
                • Mild (sleepy but responsive): 24h observation
                • Severe (unresponsive/difficult to rouse): Emergency
                
                **Behavior Changes:**
                • Document specific changes
                • Note triggers/environment
                • Video record if possible for vet
                
                **WHEN TO ESCALATE CARE:**
                ✅ Symptoms worsen despite home care
                ✅ Multiple symptoms present
                ✅ Senior/young animals
                ✅ Pre-existing medical conditions
                ✅ No improvement in 24-48 hours
                
                **TELEHEALTH PREPARATION:**
                • Take clear photos/videos
                • Record timeline of symptoms
                • List medications/supplements
                • Note diet/treat changes
                • Prepare questions for vet
                
                **URGENCY LEVEL: LOW** - Based on general description.
                
                **Recommended Action Plan:**
                1. **Immediate:** Monitor vital signs 2-3 times daily
                2. **24-hour mark:** Reassess, document changes
                3. **48-hour mark:** If no improvement, schedule vet visit
                4. **Any worsening:** Contact vet immediately
                
                **Emergency Preparedness:**
                • Keep vet/emergency numbers accessible
                • Have pet carrier ready
                • Know route to emergency clinic
                • Keep medical records organized
                """.formatted(userMessage);
        }

        public boolean isEmergencySituation(String aiResponse) {
            String lower = aiResponse.toLowerCase();
            return lower.contains("emergency") ||
                    lower.contains("urgent") ||
                    lower.contains("critical") ||
                    lower.contains("🚨") ||
                    lower.contains("go to vet now") ||
                    lower.contains("immediate") ||
                    lower.contains("lifesaving") ||
                    lower.contains("do not delay");
        }

        public ChatResponse getMockResponse(String error) {
            String response = """
                🐕 **GENERAL PET CARE ADVICE**
                
                **While we're experiencing technical issues, here's comprehensive guidance:**
                
                **For Minor Concerns:**
                • Monitor symptoms for 24-48 hours
                • Maintain hydration with fresh water
                • Provide quiet, comfortable rest area
                • Offer bland diet if digestive issues
                • Prevent access to stairs/height if mobility issues
                
                **Emergency Situations (Go to Vet NOW):**
                • Difficulty breathing or blue gums
                • Severe, uncontrolled bleeding
                • Seizures or loss of consciousness
                • Suspected poisoning or toxin ingestion
                • Trauma, fractures, or major injuries
                • Eye injuries or sudden blindness
                
                **Vital Signs to Monitor:**
                ✓ Temperature: 100.5°F - 102.5°F
                ✓ Gum Color: Pink (not pale/white/blue)
                ✓ Breathing: 15-30/min (dogs), 20-30/min (cats)
                ✓ Heart Rate: 70-160 (dogs), 140-220 (cats)
                
                **Emergency Contacts:**
                📞 Animal Poison Control: (888) 426-4435
                📞 Local Emergency Vet: Check your area
                📞 Your Regular Veterinarian: Keep number handy
                
                **Note:** Always consult a veterinarian for accurate diagnosis and treatment.
                Technical Issue: %s
                """.formatted(error);

            return new ChatResponse(true, response);
        }
    }

    private String extractLatestUserMessage(List<ChatRequest.Message> messages) {
        if (messages == null || messages.isEmpty()) {
            return "My pet needs emergency assistance";
        }

        // Get the last user message
        for (int i = messages.size() - 1; i >= 0; i--) {
            ChatRequest.Message message = messages.get(i);
            if ("user".equalsIgnoreCase(message.getRole())) {
                return message.getContent();
            }
        }

        // If no user message found, return the last message
        return messages.get(messages.size() - 1).getContent();
    }

    private String extractPetSymptom(String userMessage) {
        String lowerMessage = userMessage.toLowerCase();

        if (lowerMessage.contains("vomit") || lowerMessage.contains("throw up")) {
            return "Vomiting";
        } else if (lowerMessage.contains("bleed") || lowerMessage.contains("blood")) {
            return "Bleeding";
        } else if (lowerMessage.contains("breath") || lowerMessage.contains("choke") || lowerMessage.contains("cough")) {
            return "Breathing Issues";
        } else if (lowerMessage.contains("injure") || lowerMessage.contains("hurt") || lowerMessage.contains("wound") || lowerMessage.contains("limping")) {
            return "Injury";
        } else if (lowerMessage.contains("diarrhea") || lowerMessage.contains("loose stool")) {
            return "Diarrhea";
        } else if (lowerMessage.contains("seizure") || lowerMessage.contains("convulsion") || lowerMessage.contains("shake")) {
            return "Seizure";
        } else if (lowerMessage.contains("poison") || lowerMessage.contains("toxic") || lowerMessage.contains("chemical")) {
            return "Poisoning";
        } else if (lowerMessage.contains("eye") || lowerMessage.contains("vision")) {
            return "Eye Issues";
        } else if (lowerMessage.contains("skin") || lowerMessage.contains("rash") || lowerMessage.contains("itch")) {
            return "Skin Issues";
        }

        return "General Symptoms";
    }

    private boolean isPetRelated(String message) {
        String lowerMessage = message.toLowerCase();

        List<String> petKeywords = Arrays.asList(
                "pet", "dog", "cat", "puppy", "kitten", "animal", "vet", "veterinary",
                "paws", "tail", "fur", "whisker", "meow", "bark", "woof", "purr",
                "canine", "feline", "rabbit", "hamster", "bird", "parrot", "reptile"
        );

        List<String> healthKeywords = Arrays.asList(
                "sick", "hurt", "pain", "emergency", "symptom", "bleed", "vomit",
                "breath", "injure", "wound", "lame", "limp", "fever", "temperature",
                "diarrhea", "seizure", "poison", "toxic", "rash", "itch", "eye"
        );

        // Check if message contains pet-related keywords
        for (String keyword : petKeywords) {
            if (lowerMessage.contains(keyword)) {
                return true;
            }
        }

        // Check for health keywords in context
        int healthKeywordCount = 0;
        for (String keyword : healthKeywords) {
            if (lowerMessage.contains(keyword)) {
                healthKeywordCount++;
            }
        }

        // If multiple health keywords present, likely pet-related
        return healthKeywordCount >= 2;
    }

    public EmergencyTipsResponse extractEmergencyTips(String aiResponse) {
        try {
            List<String> careTips = new ArrayList<>();
            List<String> warnings = new ArrayList<>();

            String[] lines = aiResponse.split("\n");

            for (String line : lines) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) continue;

                // Extract care tips (usually start with bullet points or numbers)
                if (trimmed.startsWith("•") || trimmed.startsWith("-") || trimmed.startsWith("✓") ||
                        trimmed.matches("^\\d+\\..*") || trimmed.contains("recommend") ||
                        trimmed.contains("Do:") || trimmed.contains("Home care:")) {

                    String tip = trimmed.replaceAll("^[•\\-✓\\d+\\.\\s]+", "");
                    if (!tip.isEmpty() && tip.length() > 10) {

                        boolean isWarning = tip.toLowerCase().contains("seek") ||
                                tip.toLowerCase().contains("emergency") ||
                                tip.toLowerCase().contains("immediately") ||
                                tip.toLowerCase().contains("urgent") ||
                                tip.toLowerCase().contains("go to vet") ||
                                tip.toLowerCase().contains("do not delay") ||
                                tip.contains("🚨") ||
                                tip.contains("⚠️");

                        if (isWarning) {
                            warnings.add(tip);
                        } else {
                            careTips.add(tip);
                        }
                    }
                }

                // Extract urgent warnings from warning sections
                if (trimmed.toLowerCase().contains("warning:") ||
                        trimmed.toLowerCase().contains("emergency:") ||
                        trimmed.toLowerCase().contains("red flags:") ||
                        trimmed.toLowerCase().contains("urgent:")) {
                    warnings.add(trimmed);
                }
            }

            // If no specific tips found, create comprehensive ones
            if (careTips.isEmpty()) {
                careTips = Arrays.asList(
                        "Keep your pet calm and in a quiet, comfortable environment",
                        "Ensure fresh water is available at all times to prevent dehydration",
                        "Monitor vital signs: breathing rate, gum color, and temperature regularly",
                        "Do not administer human medications without veterinary consultation"
                );
            }

            if (warnings.isEmpty()) {
                warnings = Arrays.asList(
                        "Difficulty breathing, blue gums, or open-mouth breathing in cats",
                        "Severe bleeding that does not stop with direct pressure",
                        "Loss of consciousness, seizures, or inability to stand/walk",
                        "Suspected poisoning, toxin ingestion, or chemical exposure"
                );
            }

            // Limit to 4 items each
            return new EmergencyTipsResponse(true,
                    careTips.subList(0, Math.min(careTips.size(), 4)),
                    warnings.subList(0, Math.min(warnings.size(), 4)));

        } catch (Exception e) {
            e.printStackTrace();
            // Return comprehensive default tips
            return new EmergencyTipsResponse(true,
                    Arrays.asList(
                            "Provide a quiet, stress-free environment for your pet",
                            "Ensure constant access to fresh, clean water",
                            "Monitor breathing and gum color every few hours",
                            "Keep your pet from stairs or heights if mobility is affected"
                    ),
                    Arrays.asList(
                            "Breathing difficulties or blue/pale gums",
                            "Uncontrolled bleeding or blood in vomit/stool",
                            "Seizures, collapse, or loss of consciousness",
                            "Suspected poisoning or toxin ingestion"
                    ),
                    "Using default emergency guidelines");
        }
    }
}