package com.shelter_ms.controller;

import com.shelter_ms.dto.LoginRequest;
import com.shelter_ms.dto.LoginResponse;
import com.shelter_ms.entity.Register;
import com.shelter_ms.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    @Autowired
    private RegisterRepository registerRepo;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginData) {

        Register user = registerRepo.findByEmail(loginData.getEmail())
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(404).build();
        }

        if (!user.getStatus().equals("APPROVED")) {
            return ResponseEntity.status(403).build();
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(loginData.getPassword(), user.getPassword())) {

            // ✅ RETURN EMAIL HERE
            LoginResponse response = new LoginResponse(
                    user.getEmail(),
                    "shelter"
            );

            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.status(401).build();
        }
    }


}
