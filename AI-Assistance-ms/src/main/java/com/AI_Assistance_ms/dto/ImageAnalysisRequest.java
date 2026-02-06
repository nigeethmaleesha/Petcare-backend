// src/main/java/com/AI_Assistance_ms/dto/request/ImageAnalysisRequest.java
package com.AI_Assistance_ms.dto;

public class ImageAnalysisRequest {
    private String imageBase64;
    private String description;
    private String sessionId;

    public String getImageBase64() { return imageBase64; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
}