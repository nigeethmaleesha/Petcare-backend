package com.admin_ms.dto;

public class LoginResponseDTO {
    private String message;
    private int adminId;
    private String fullName;
    private String email;
    private String role;

    // Constructors
    public LoginResponseDTO() {}

    public LoginResponseDTO(String message, int adminId, String fullName, String email, String role) {
        this.message = message;
        this.adminId = adminId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}