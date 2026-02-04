package com.petowner_ms.dto;

import java.math.BigDecimal;
import java.sql.Date;

public class GrowthRecordDTO {
    private Date measurementDate;
    private BigDecimal weight;
    private BigDecimal height;
    private String notes;

    // Getters and Setters
    public Date getMeasurementDate() { return measurementDate; }
    public void setMeasurementDate(Date measurementDate) { this.measurementDate = measurementDate; }

    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }

    public BigDecimal getHeight() { return height; }
    public void setHeight(BigDecimal height) { this.height = height; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}