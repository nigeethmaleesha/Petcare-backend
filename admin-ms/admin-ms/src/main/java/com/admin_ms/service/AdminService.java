package com.admin_ms.service;

import com.admin_ms.data.Admin;
import com.admin_ms.data.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // Login admin
    public Optional<Admin> login(String email, String password) {
        return adminRepository.findByEmailAndPassword(email, password);
    }

    // Get admin by ID
    public Optional<Admin> getAdminById(int id) {
        return adminRepository.findById(id);
    }

    // Get admin by email
    public Optional<Admin> getAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }
}