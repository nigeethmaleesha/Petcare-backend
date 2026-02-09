package com.Adoption_ms.data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "adoption")
public class Adoption {

    @Id
    private String adoption_id;   // PET-001

    private String pet_name;
    private String breed;
    private String species;
    private int age;
    private String size;

    @Column(name = "shelter_id")
    private String shelterId;     // REG-001 ✅

    private boolean vaccinated;
    private boolean kid_friendly;

    @Column(columnDefinition = "TEXT")
    private String medical_notes;

    @Column(columnDefinition = "TEXT")
    private String special_needs;

    private String image_path;
    private LocalDateTime created_at;

    @PrePersist
    protected void onCreate() {
        created_at = LocalDateTime.now();
    }

    // Getters & Setters
    public String getAdoption_id() { return adoption_id; }
    public void setAdoption_id(String adoption_id) { this.adoption_id = adoption_id; }

    public String getPet_name() { return pet_name; }
    public void setPet_name(String pet_name) { this.pet_name = pet_name; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getShelterId() { return shelterId; }
    public void setShelterId(String shelterId) { this.shelterId = shelterId; }

    public boolean isVaccinated() { return vaccinated; }
    public void setVaccinated(boolean vaccinated) { this.vaccinated = vaccinated; }

    public boolean isKid_friendly() { return kid_friendly; }
    public void setKid_friendly(boolean kid_friendly) { this.kid_friendly = kid_friendly; }

    public String getMedical_notes() { return medical_notes; }
    public void setMedical_notes(String medical_notes) { this.medical_notes = medical_notes; }

    public String getSpecial_needs() { return special_needs; }
    public void setSpecial_needs(String special_needs) { this.special_needs = special_needs; }

    public String getImage_path() { return image_path; }
    public void setImage_path(String image_path) { this.image_path = image_path; }

    public LocalDateTime getCreated_at() { return created_at; }
}
