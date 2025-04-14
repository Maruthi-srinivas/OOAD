package com.example.telecom.controller;

import com.example.Telecom_buliding_system.entity.User;
import com.example.Telecom_buliding_system.repository.BillRepository;
import com.example.Telecom_buliding_system.repository.PlanRepository;
import com.example.Telecom_buliding_system.util.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    private final BillRepository billRepository;
    private final PlanRepository planRepository;

    public MainController(BillRepository billRepository, PlanRepository planRepository) {
        this.billRepository = billRepository;
        this.planRepository = planRepository;
    }

    // Home Page
    @GetMapping("/")
    public String home(HttpSession session, Model model, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        String token = (String) session.getAttribute("jwtToken");

        if (loggedInUser == null || token == null || JwtUtil.isTokenValid(token) == false) {
            return "redirect:/login";
        }
        // Fetch user-specific data
        Long userId = Long.parseLong(JwtUtil.getUserIdFromToken(token));
        model.addAttribute("bills", billRepository.findByUserId(userId));
        model.addAttribute("plans", planRepository.findAll());

        return "index"; // Renders index.html
    }

    // Plans Page
    @GetMapping("/plans")
    public String plans(HttpSession session, Model model) {
        // Add plans data to the model (mock data for now)
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        String token = (String) session.getAttribute("jwtToken");

        if (loggedInUser == null || token == null || JwtUtil.isTokenValid(token) == false) {
            return "redirect:/login";
        }
        model.addAttribute("plans", new String[] { "Plan A", "Plan B", "Plan C" });
        return "plans"; // Renders plans.html
    }

    // Bills Page
    @GetMapping("/bills")
    public String bills(HttpSession session, Model model) {
        // Add bills data to the model
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        String token = (String) session.getAttribute("jwtToken");

        if (loggedInUser == null || token == null || JwtUtil.isTokenValid(token) == false) {
            return "redirect:/login";
        }
        model.addAttribute("bills", new String[] { "Bill 1", "Bill 2", "Bill 3" });
        return "bills"; // Renders bills.html
    }

    // Handle Create Bill Form Submission
    @PostMapping("/bills")
    public String createBill(@RequestParam("userId") String userId,
            @RequestParam("amount") double amount,
            @RequestParam("planId") String planId,
            Model model) {
        // Process the bill creation (mock logic for now)
        model.addAttribute("message", "Bill created successfully for User ID: " + userId);
        return "bills"; // Redirect to bills.html
    }
}
