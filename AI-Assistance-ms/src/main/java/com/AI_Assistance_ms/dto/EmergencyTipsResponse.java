package com.AI_Assistance_ms.dto;

import java.util.List;

public class EmergencyTipsResponse {
    private boolean success;
    private List<String> careTips;
    private List<String> warningSymptoms;
    private String error;

    public EmergencyTipsResponse() {}

    public EmergencyTipsResponse(boolean success, List<String> careTips, List<String> warningSymptoms) {
        this.success = success;
        this.careTips = careTips;
        this.warningSymptoms = warningSymptoms;
    }

    public EmergencyTipsResponse(boolean success, List<String> careTips, List<String> warningSymptoms, String error) {
        this.success = success;
        this.careTips = careTips;
        this.warningSymptoms = warningSymptoms;
        this.error = error;
    }

    public EmergencyTipsResponse(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public List<String> getCareTips() { return careTips; }
    public void setCareTips(List<String> careTips) { this.careTips = careTips; }

    public List<String> getWarningSymptoms() { return warningSymptoms; }
    public void setWarningSymptoms(List<String> warningSymptoms) { this.warningSymptoms = warningSymptoms; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}