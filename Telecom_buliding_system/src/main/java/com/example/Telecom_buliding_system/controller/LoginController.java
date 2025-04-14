package com.example.Telecom_buliding_system.controller;

import com.example.Telecom_buliding_system.entity.User;
import com.example.Telecom_buliding_system.repository.UserRepository;
import com.example.Telecom_buliding_system.repository.BillRepository;
import com.example.Telecom_buliding_system.repository.PlanRepository;
import com.example.Telecom_buliding_system.util.JwtUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

@Controller
public class LoginController {

    private final UserRepository userRepository;
    private final BillRepository billRepository;
    private final PlanRepository planRepository;

    public LoginController(UserRepository userRepository, BillRepository billRepository,
            PlanRepository planRepository) {
        this.userRepository = userRepository;
        this.billRepository = billRepository;
        this.planRepository = planRepository;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("name") String name,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {
        User user = userRepository.findByName(name);
        if (user != null && user.getPassword().equals(password)) {

            String token = JwtUtil.generateToken(String.valueOf(user.getId()), user.getName());
            session.setAttribute("loggedInUser", user);
            session.setAttribute("jwtToken", token);

            // Fetch user-specific data
            model.addAttribute("bills", billRepository.findByUserId(user.getId()));
            model.addAttribute("plans", planRepository.findAll());

            return "index"; // Redirect to the home page and display user data
        }
        model.addAttribute("error", "Invalid credentials!");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        session.invalidate();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        return "redirect:/login";
    }
}