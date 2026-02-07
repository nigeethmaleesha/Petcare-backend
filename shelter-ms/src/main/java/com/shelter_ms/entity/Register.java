package com.shelter_ms.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "register")
public class Register {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "full_name")
    private String fullName;

    @Column(unique = true)
    private String email;

    private String password;


    @Column(name = "shelter_name")
    private String shelterName;

    @Column(name = "license_number")
    private String licenseNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String address;

    @Column(length = 1000)
    private String description;

    @Column(name = "document_path")
    private String documentPath;

    private String status = "PENDING";

    public Register() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getShelterName() { return shelterName; }
    public void setShelterName(String shelterName) { this.shelterName = shelterName; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDocumentPath() { return documentPath; }
    public void setDocumentPath(String documentPath) { this.documentPath = documentPath; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
