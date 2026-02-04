package com.admin_ms.service;

import com.admin_ms.data.Admin;
import com.admin_ms.dto.LoginDTO;
import com.admin_ms.dto.LoginResponseDTO;
import com.admin_ms.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoderService passwordEncoderService;

    public LoginResponseDTO loginAdmin(LoginDTO loginDTO) {
        // Validate input
        if (loginDTO.getEmail() == null || loginDTO.getEmail().isEmpty() ||
                loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {
            throw new RuntimeException("Email and password are required");
        }

        // Find admin by email
        Optional<Admin> adminOptional = adminRepository.findByEmail(loginDTO.getEmail());

        if (!adminOptional.isPresent()) {
            throw new RuntimeException("Admin not found with email: " + loginDTO.getEmail());
        }

        Admin admin = adminOptional.get();

        // Check if admin is active
        if (!"active".equalsIgnoreCase(admin.getStatus())) {
            throw new RuntimeException("Admin account is not active. Status: " + admin.getStatus());
        }

        // Verify password
        boolean passwordMatches = passwordEncoderService.matches(
                loginDTO.getPassword(),
                admin.getPassword()
        );

        if (!passwordMatches) {
            throw new RuntimeException("Invalid password");
        }

        // Update last login time
        admin.setLastLogin(LocalDateTime.now());
        adminRepository.save(admin);

        // Return success response
        return new LoginResponseDTO(
                "Login successful",
                admin.getAdminId(),
                admin.getFullName(),
                admin.getEmail(),
                admin.getRole()
        );
    }

    public boolean verifyAdmin(int adminId) {
        Optional<Admin> adminOptional = adminRepository.findById(adminId);
        return adminOptional.isPresent() && "active".equalsIgnoreCase(adminOptional.get().getStatus());
    }
}