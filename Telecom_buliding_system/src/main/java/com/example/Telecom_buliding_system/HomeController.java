package com.example.Telecom_buliding_system;

import com.example.Telecom_buliding_system.entity.Plan;
import com.example.Telecom_buliding_system.repository.PlanRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final PlanRepository planRepository;

    public HomeController(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Plan> plans = planRepository.findAll();
        model.addAttribute("plans", plans);
        return "index";
    }
}