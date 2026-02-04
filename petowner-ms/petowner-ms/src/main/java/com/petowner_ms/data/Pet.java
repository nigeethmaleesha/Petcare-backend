package com.petowner_ms.data;

import jakarta.persistence.*;
import java.sql.Date;
import java.math.BigDecimal;

@Entity
@Table(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private int petId;

    @Column(name = "pet_owner_id", nullable = false)
    private int petOwnerId;

    @Column(name = "pet_name", nullable = false)
    private String petName;

    @Column(nullable = false)
    private String species;

    private String breed;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    private BigDecimal weight;

    private BigDecimal height;

    @Column(name = "health_notes", columnDefinition = "TEXT")
    private String healthNotes;

    @Column(name = "image_url")
    private String imageUrl;

    private String status = "Active";

    // Getters and Setters
    public int getPetId() { return petId; }
    public void setPetId(int petId) { this.petId = petId; }

    public int getPetOwnerId() { return petOwnerId; }
    public void setPetOwnerId(int petOwnerId) { this.petOwnerId = petOwnerId; }

    public String getPetName() { return petName; }
    public void setPetName(String petName) { this.petName = petName; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }

    public BigDecimal getHeight() { return height; }
    public void setHeight(BigDecimal height) { this.height = height; }

    public String getHealthNotes() { return healthNotes; }
    public void setHealthNotes(String healthNotes) { this.healthNotes = healthNotes; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}