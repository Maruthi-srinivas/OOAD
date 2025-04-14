package com.example.Telecom_buliding_system.controller;

import com.example.Telecom_buliding_system.entity.ContactMessage;
import com.example.Telecom_buliding_system.repository.ContactMessageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class ContactController {

    private final ContactMessageRepository contactMessageRepository;

    public ContactController(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    @GetMapping("/contact")
    public String contactUs(Model model) {
        model.addAttribute("customerCareNumber", "1800-123-4567");
        model.addAttribute("email", "support@telecombilling.com");
        model.addAttribute("workingHours", "Monday to Friday, 9 AM to 6 PM");
        return "contact";
    }

    @PostMapping("/contact")
    public String handleContactForm(@RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("message") String message,
            Model model) {
        // Save the message to the database
        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setName(name);
        contactMessage.setEmail(email);
        contactMessage.setMessage(message);
        contactMessage.setSubmittedAt(new Date());
        contactMessageRepository.save(contactMessage);

        // Add a success message to the model
        model.addAttribute("successMessage", "Thank you, " + name + "! Your message has been received. We will get back to you shortly.");
        return "contact";
    }
}
