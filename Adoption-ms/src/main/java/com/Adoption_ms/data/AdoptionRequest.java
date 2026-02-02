package com.Adoption_ms.data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "adoption_requests")
public class AdoptionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int request_id;

    private LocalDateTime request_date = LocalDateTime.now();

    private int adoption_id;
    private String pet_name;
    private int shelter_id;

    private String type_of_home;

    @Enumerated(EnumType.STRING)
    private FencedYard fenced_yard = FencedYard.No;

    private String activity_level;
    private int hours_alone_per_day;

    private String status = "Pending";

    public enum FencedYard { Yes, No }

    // Getters and Setters
    public int getRequest_id() { return request_id; }
    public void setRequest_id(int request_id) { this.request_id = request_id; }

    public LocalDateTime getRequest_date() { return request_date; }
    public void setRequest_date(LocalDateTime request_date) { this.request_date = request_date; }

    public int getAdoption_id() { return adoption_id; }
    public void setAdoption_id(int adoption_id) { this.adoption_id = adoption_id; }

    public String getPet_name() { return pet_name; }
    public void setPet_name(String pet_name) { this.pet_name = pet_name; }

    public int getShelter_id() { return shelter_id; }
    public void setShelter_id(int shelter_id) { this.shelter_id = shelter_id; }

    public String getType_of_home() { return type_of_home; }
    public void setType_of_home(String type_of_home) { this.type_of_home = type_of_home; }

    public FencedYard getFenced_yard() { return fenced_yard; }
    public void setFenced_yard(FencedYard fenced_yard) { this.fenced_yard = fenced_yard; }

    public String getActivity_level() { return activity_level; }
    public void setActivity_level(String activity_level) { this.activity_level = activity_level; }

    public int getHours_alone_per_day() { return hours_alone_per_day; }
    public void setHours_alone_per_day(int hours_alone_per_day) { this.hours_alone_per_day = hours_alone_per_day; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
