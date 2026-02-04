package com.petowner_ms.dto;

import java.sql.Date;

public class MedicalRecordDTO {
    private Date recordDate;
    private String conditionName;
    private String recordType; // Routine, Illness, Vaccine, Injury, Other
    private String treatment;
    private String notes;

    // Getters and Setters
    public Date getRecordDate() { return recordDate; }
    public void setRecordDate(Date recordDate) { this.recordDate = recordDate; }

    public String getConditionName() { return conditionName; }
    public void setConditionName(String conditionName) { this.conditionName = conditionName; }

    public String getRecordType() { return recordType; }
    public void setRecordType(String recordType) { this.recordType = recordType; }

    public String getTreatment() { return treatment; }
    public void setTreatment(String treatment) { this.treatment = treatment; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}