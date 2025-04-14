package com.example.Telecom_buliding_system.controller;

import com.example.Telecom_buliding_system.entity.User;
import com.example.Telecom_buliding_system.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;

    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Display registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    // Handle registration form submission
    @PostMapping("/register")
    public String registerUser(@RequestParam("password") String password,
                               @RequestParam("confirmPassword") String confirmPassword,
                               @ModelAttribute User user,
                               Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("message", "Passwords do not match!");
            return "registration";
        }

        user.setPassword(password);
        user.setRole("USER"); // Default role is USER
        userRepository.save(user);
        model.addAttribute("message", "User registered successfully! Please log in.");
        return "redirect:/login";
    }
}