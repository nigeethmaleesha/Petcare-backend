package com.petowner_ms.data;

import jakarta.persistence.*;
import java.sql.Date;
import java.math.BigDecimal;

@Entity
@Table(name = "growth_record")
public class GrowthRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "growth_id")
    private int growthId;

    @Column(name = "pet_id", nullable = false)
    private int petId;

    @Column(name = "measurement_date", nullable = false)
    private Date measurementDate;

    @Column(nullable = false)
    private BigDecimal weight;

    @Column(nullable = false)
    private BigDecimal height;

    private String notes;

    // Getters and Setters
    public int getGrowthId() { return growthId; }
    public void setGrowthId(int growthId) { this.growthId = growthId; }

    public int getPetId() { return petId; }
    public void setPetId(int petId) { this.petId = petId; }

    public Date getMeasurementDate() { return measurementDate; }
    public void setMeasurementDate(Date measurementDate) { this.measurementDate = measurementDate; }

    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }

    public BigDecimal getHeight() { return height; }
    public void setHeight(BigDecimal height) { this.height = height; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}