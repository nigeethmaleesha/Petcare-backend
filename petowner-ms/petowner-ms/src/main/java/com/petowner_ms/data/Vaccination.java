package com.petowner_ms.data;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "vaccination")
public class Vaccination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccination_id")
    private int vaccinationId;

    @Column(name = "pet_id", nullable = false)
    private int petId;

    @Column(name = "vaccine_name", nullable = false)
    private String vaccineName;

    @Column(name = "vaccination_date", nullable = false)
    private Date vaccinationDate;

    @Column(name = "clinic_name")
    private String clinicName;

    @Column(name = "veterinarian_name")
    private String veterinarianName;

    private String notes;

    @Column(name = "next_due_date")
    private Date nextDueDate;

    @Column(name = "reminder_set")
    private boolean reminderSet = true;

    private String status = "Planned";

    // Getters and Setters
    public int getVaccinationId() { return vaccinationId; }
    public void setVaccinationId(int vaccinationId) { this.vaccinationId = vaccinationId; }

    public int getPetId() { return petId; }
    public void setPetId(int petId) { this.petId = petId; }

    public String getVaccineName() { return vaccineName; }
    public void setVaccineName(String vaccineName) { this.vaccineName = vaccineName; }

    public Date getVaccinationDate() { return vaccinationDate; }
    public void setVaccinationDate(Date vaccinationDate) { this.vaccinationDate = vaccinationDate; }

    public String getClinicName() { return clinicName; }
    public void setClinicName(String clinicName) { this.clinicName = clinicName; }

    public String getVeterinarianName() { return veterinarianName; }
    public void setVeterinarianName(String veterinarianName) { this.veterinarianName = veterinarianName; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Date getNextDueDate() { return nextDueDate; }
    public void setNextDueDate(Date nextDueDate) { this.nextDueDate = nextDueDate; }

    public boolean isReminderSet() { return reminderSet; }
    public void setReminderSet(boolean reminderSet) { this.reminderSet = reminderSet; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}