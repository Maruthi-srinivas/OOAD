package com.example.Telecom_buliding_system.controller;

import com.example.Telecom_buliding_system.entity.User;
import com.example.Telecom_buliding_system.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TokenController {

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            String newToken = JwtUtil.generateToken(String.valueOf(user.getId()), user.getName());
            session.setAttribute("jwtToken", newToken);

            Map<String, String> response = new HashMap<>();
            response.put("token", newToken);
            return ResponseEntity.ok(response);
        }

        Map<String, String> response = new HashMap<>();
        response.put("error", "Not authenticated");
        return ResponseEntity.badRequest().body(response);
    }
}