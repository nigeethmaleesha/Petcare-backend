package com.AI_Assistance_ms.dto;

public class ChatResponse {
    private boolean success;
    private String text;
    private String error;
    private String sessionId;
    private boolean isEmergency;

    public ChatResponse() {}

    public ChatResponse(boolean success, String text) {
        this.success = success;
        this.text = text;
    }

    public ChatResponse(boolean success, String text, String error) {
        this.success = success;
        this.text = text;
        this.error = error;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public boolean isEmergency() { return isEmergency; }
    public void setEmergency(boolean emergency) { isEmergency = emergency; }
}