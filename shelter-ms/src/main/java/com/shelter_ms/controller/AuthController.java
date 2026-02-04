package com.shelter_ms.controller;

import com.shelter_ms.dto.LoginRequest;
import com.shelter_ms.entity.Register;
import com.shelter_ms.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    @Autowired
    private RegisterRepository registerRepo;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginData) {

        Register user = registerRepo.findByEmail(loginData.getEmail())
                .orElse(null);

        if (user == null) {
            return "Email not found";
        }

        if (!user.getStatus().equals("APPROVED")) {
            return "Not approved by admin yet";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(loginData.getPassword(), user.getPassword())) {
            return "LOGIN_SUCCESS";
        }
     else {
            return "Wrong password";
        }
    }
}
