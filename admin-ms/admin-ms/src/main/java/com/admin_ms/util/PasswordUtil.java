package com.example.admin_ms.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordUtil {

    // Simple SHA-256 hashing for demo purposes
    // In production, use proper password hashing like BCrypt
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        String hashedInput = hashPassword(password);
        return hashedInput.equals(hashedPassword);
    }

    // For initial setup - create hashed password for admin
    public static void main(String[] args) {
        String password = "admin123";
        String hashed = hashPassword(password);
        System.out.println("Password: " + password);
        System.out.println("Hashed: " + hashed);

        // Test verification
        System.out.println("Verify: " + verifyPassword("admin123", hashed));
        System.out.println("Wrong password: " + verifyPassword("wrong", hashed));
    }
}