package com.AI_Assistance_ms.data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_history")
public class ChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sessionId;

    @Column(columnDefinition = "TEXT")
    private String userMessage;

    @Column(columnDefinition = "TEXT")
    private String aiResponse;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column
    private String petSymptom;

    @Column
    private boolean isEmergency = false;

    public ChatHistory() {}

    public ChatHistory(String sessionId, String userMessage, String aiResponse, String petSymptom) {
        this.sessionId = sessionId;
        this.userMessage = userMessage;
        this.aiResponse = aiResponse;
        this.petSymptom = petSymptom;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getUserMessage() { return userMessage; }
    public void setUserMessage(String userMessage) { this.userMessage = userMessage; }

    public String getAiResponse() { return aiResponse; }
    public void setAiResponse(String aiResponse) { this.aiResponse = aiResponse; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getPetSymptom() { return petSymptom; }
    public void setPetSymptom(String petSymptom) { this.petSymptom = petSymptom; }

    public boolean isEmergency() { return isEmergency; }
    public void setEmergency(boolean emergency) { isEmergency = emergency; }
}