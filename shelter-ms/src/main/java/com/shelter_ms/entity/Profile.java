package com.shelter_ms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Profile {

    @Id
    private String regId;

    private String shelterName;
    private String licenseNumber;
    private String email;
    private String password;
    private String phone;
    private String description;
    private String address;
    private String profileImage;

    public Profile() {}

    public String getRegId() { return regId; }
    public void setRegId(String regId) { this.regId = regId; }

    public String getShelterName() { return shelterName; }
    public void setShelterName(String shelterName) { this.shelterName = shelterName; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }
}
