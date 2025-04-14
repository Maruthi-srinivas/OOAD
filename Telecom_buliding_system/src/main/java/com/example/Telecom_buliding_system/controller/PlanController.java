package com.example.Telecom_buliding_system.controller;

import com.example.Telecom_buliding_system.entity.Plan;
import com.example.Telecom_buliding_system.repository.PlanRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PlanController {

    private final PlanRepository planRepository;

    public PlanController(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    // Display the plans page
    @GetMapping("/plans")
    public String showPlans(Model model) {
        // Fetch plans from the database
        List<Plan> plans = planRepository.findAll();
        model.addAttribute("plans", plans);
        return "plans"; // Renders plans.html
    }
}