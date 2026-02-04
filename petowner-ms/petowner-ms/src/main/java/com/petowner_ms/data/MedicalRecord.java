package com.petowner_ms.data;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "medical_record")
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medical_id")
    private int medicalId;

    @Column(name = "pet_id", nullable = false)
    private int petId;

    @Column(name = "record_date", nullable = false)
    private Date recordDate;

    @Column(name = "condition_name", nullable = false)
    private String conditionName;

    @Column(name = "record_type", nullable = false)
    private String recordType; // Routine, Illness, Vaccine, Injury, Other

    private String treatment;

    private String notes;

    // Getters and Setters
    public int getMedicalId() { return medicalId; }
    public void setMedicalId(int medicalId) { this.medicalId = medicalId; }

    public int getPetId() { return petId; }
    public void setPetId(int petId) { this.petId = petId; }

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