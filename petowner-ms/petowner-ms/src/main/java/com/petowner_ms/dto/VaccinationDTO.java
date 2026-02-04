package com.petowner_ms.dto;

import java.sql.Date;

public class VaccinationDTO {
    private String vaccineName;
    private Date vaccinationDate;
    private String clinicName;
    private String veterinarianName;
    private String notes;
    private Date nextDueDate;
    private boolean reminderSet = true;
    private String status = "Planned";

    // Getters and Setters
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