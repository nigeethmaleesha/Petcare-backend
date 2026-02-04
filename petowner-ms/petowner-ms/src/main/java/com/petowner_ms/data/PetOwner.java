package com.petowner_ms.data;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "pet_owner")
public class PetOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_owner_id")
    private int id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "pet-owner";

    @Column(nullable = false)
    private boolean agree;

    @Column(name = "created_at")
    private Timestamp createdAt;

    // Constructors
    public PetOwner() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.role = "pet-owner";
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isAgree() { return agree; }
    public void setAgree(boolean agree) { this.agree = agree; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}