package com.admin_ms.controller;

import com.admin_ms.data.Admin;
import com.admin_ms.dto.LoginRequest;
import com.admin_ms.dto.LoginResponse;
import com.admin_ms.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Login admin
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Optional<Admin> adminOpt = adminService.login(request.getEmail(), request.getPassword());

            if (adminOpt.isPresent()) {
                Admin admin = adminOpt.get();
                LoginResponse response = new LoginResponse(
                        "Login successful",
                        admin.getId(),
                        admin.getFullName(),
                        admin.getEmail(),
                        admin.getRole()
                );
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid email or password"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Login failed"));
        }
    }

    // Get admin by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getAdminById(@PathVariable int id) {
        try {
            Optional<Admin> admin = adminService.getAdminById(id);

            if (admin.isPresent()) {
                return ResponseEntity.ok(admin.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Admin not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch admin"));
        }
    }

    // Get admin by email
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getAdminByEmail(@PathVariable String email) {
        try {
            Optional<Admin> admin = adminService.getAdminByEmail(email);

            if (admin.isPresent()) {
                return ResponseEntity.ok(admin.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Admin not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch admin"));
        }
    }
}