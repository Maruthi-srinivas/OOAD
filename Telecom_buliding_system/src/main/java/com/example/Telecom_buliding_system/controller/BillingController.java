//all bills eventhough it is not logged in user 
// package com.example.Telecom_buliding_system.controller;

// import com.example.Telecom_buliding_system.entity.Bill;
// import com.example.Telecom_buliding_system.entity.Plan;
// import com.example.Telecom_buliding_system.entity.User;
// import com.example.Telecom_buliding_system.repository.BillRepository;
// import com.example.Telecom_buliding_system.repository.PlanRepository;
// import com.example.Telecom_buliding_system.repository.UserRepository;
// import com.example.Telecom_buliding_system.service.BillingService;
// import jakarta.servlet.http.HttpSession;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.*;
// import java.util.List;
// import java.util.Optional;

// @Controller
// // @RequestMapping("/bills")
// public class BillingController {

//     private final BillRepository billRepository;
//     private final UserRepository userRepository;
//     private final PlanRepository planRepository;
//     private final BillingService billingService; // Add BillingService

//     public BillingController(BillRepository billRepository,
//             UserRepository userRepository,
//             PlanRepository planRepository,
//             BillingService billingService) { // Inject BillingService
//         this.billRepository = billRepository;
//         this.userRepository = userRepository;
//         this.planRepository = planRepository;
//         this.billingService = billingService; // Initialize BillingService
//     }

//     @PostMapping
//     public String createBill(@RequestParam("userId") Long userId,
//             @RequestParam("amount") Double amount,
//             @RequestParam("planId") Long planId,
//             Model model) {
//         // Fetch User and Plan from the database
//         Optional<User> user = userRepository.findById(userId);
//         Optional<Plan> plan = planRepository.findById(planId);

//         if (user.isPresent() && plan.isPresent()) {
//             // Create and save the bill using BillingService
//             Bill bill = new Bill();
//             bill.setUser(user.get());
//             bill.setPlan(plan.get());
//             bill.setAmount(amount);
//             bill.setStatus("Pending");
//             billingService.createBill(bill); // Use BillingService to save the bill

//             model.addAttribute("message", "Bill created successfully!");
//             return "redirect:/bills"; // Redirect to the bills page
//         } else {
//             model.addAttribute("error", "Invalid User ID or Plan ID.");
//             return "redirect:/plans"; // Redirect to plans page if invalid
//         }
//     }

//     @GetMapping
//     public String getAllBills(Model model) {
//         // Fetch all bills from the database
//         model.addAttribute("bills", billingService.getAllBills());
//         return "bills"; // Render the bills.html template
//     }

//     @GetMapping("/create")
//     public String createBillForm(@RequestParam("planId") Long planId,
//             @RequestParam("amount") Double amount,
//             HttpSession session,
//             Model model) {
//         // Fetch the logged-in user
//         User loggedInUser = (User) session.getAttribute("loggedInUser");
//         if (loggedInUser == null) {
//             return "redirect:/login"; // Redirect to login if no user is logged in
//         }

//         // Fetch the plan details
//         Optional<Plan> plan = planRepository.findById(planId);
//         if (plan.isPresent()) {
//             model.addAttribute("userId", loggedInUser.getId());
//             model.addAttribute("userName", loggedInUser.getName());
//             model.addAttribute("planId", plan.get().getId());
//             model.addAttribute("planName", plan.get().getName());
//             model.addAttribute("amount", amount);
//             return "create-bill"; // Render the create-bill.html template
//         } else {
//             model.addAttribute("error", "Invalid Plan ID.");
//             return "redirect:/plans"; // Redirect to plans page if the plan is invalid
//         }
//     }
// }


//only user can see the bills and admin can see all the bills

// package com.example.Telecom_buliding_system.controller;

// import com.example.Telecom_buliding_system.entity.Bill;
// import com.example.Telecom_buliding_system.entity.User;
// import com.example.Telecom_buliding_system.repository.BillRepository;
// import jakarta.servlet.http.HttpSession;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;

// import java.util.List;

// @Controller
// public class BillingController {

//     private final BillRepository billRepository;

//     public BillingController(BillRepository billRepository) {
//         this.billRepository = billRepository;
//     }

//     @GetMapping("/bills")
//     public String getBills(HttpSession session, Model model) {
//         User loggedInUser = (User) session.getAttribute("loggedInUser");

//         if (loggedInUser == null) {
//             return "redirect:/login"; // Redirect to login if no user is logged in
//         }

//         if ("ADMIN".equals(loggedInUser.getRole())) {
//             // Admin can see all bills
//             List<Bill> allBills = billRepository.findAll();
//             model.addAttribute("bills", allBills);
//         } else {
//             // Regular user can only see their own bills
//             List<Bill> userBills = billRepository.findByUserId(loggedInUser.getId());
//             model.addAttribute("bills", userBills);
//         }

//         return "bills"; // Render the bills.html template
//     }
// }




package com.example.Telecom_buliding_system.controller;

import com.example.Telecom_buliding_system.entity.Bill;
import com.example.Telecom_buliding_system.entity.Plan;
import com.example.Telecom_buliding_system.entity.User;
import com.example.Telecom_buliding_system.repository.BillRepository;
import com.example.Telecom_buliding_system.repository.PlanRepository;
import com.example.Telecom_buliding_system.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class BillingController {

    private final BillRepository billRepository;
    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    public BillingController(BillRepository billRepository, PlanRepository planRepository, UserRepository userRepository) {
        this.billRepository = billRepository;
        this.planRepository = planRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/bills/create")
    public String createBillForm(@RequestParam("planId") Long planId,
                                 @RequestParam("amount") Double amount,
                                 HttpSession session,
                                 Model model) {
        // Fetch the logged-in user
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; // Redirect to login if no user is logged in
        }

        // Fetch the plan details
        Optional<Plan> plan = planRepository.findById(planId);
        if (plan.isPresent()) {
            model.addAttribute("userId", loggedInUser.getId());
            model.addAttribute("userName", loggedInUser.getName());
            model.addAttribute("planId", plan.get().getId());
            model.addAttribute("planName", plan.get().getName());
            model.addAttribute("amount", amount);
            return "create-bill"; // Render the create-bill.html template
        } else {
            model.addAttribute("error", "Invalid Plan ID.");
            return "redirect:/plans"; // Redirect to plans page if the plan is invalid
        }
    }

    @PostMapping("/bills")
    public String createBill(@RequestParam("userId") Long userId,
                             @RequestParam("planId") Long planId,
                             @RequestParam("amount") Double amount,
                             Model model) {
        // Fetch the user and plan
        Optional<User> user = userRepository.findById(userId);
        Optional<Plan> plan = planRepository.findById(planId);

        if (user.isPresent() && plan.isPresent()) {
            // Create and save the bill
            Bill bill = new Bill();
            bill.setUser(user.get());
            bill.setPlan(plan.get());
            bill.setAmount(amount);
            bill.setStatus("Pending");
            billRepository.save(bill);

            model.addAttribute("message", "Bill created successfully!");
            return "redirect:/bills"; // Redirect to the bills page
        } else {
            model.addAttribute("error", "Invalid User ID or Plan ID.");
            return "redirect:/plans"; // Redirect to plans page if invalid
        }
    }

    @GetMapping("/bills")
    public String getBills(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login"; // Redirect to login if no user is logged in
        }

        model.addAttribute("loggedInUser", loggedInUser);

        if ("ADMIN".equalsIgnoreCase(loggedInUser.getRole())) {
            List<Bill> allBills = billRepository.findAll();
            List<Bill> conflictBills = billRepository.findByConflictRaised(true);
            model.addAttribute("bills", allBills);
            model.addAttribute("conflictBills", conflictBills);
        } else {
            List<Bill> userBills = billRepository.findByUserId(loggedInUser.getId());
            model.addAttribute("bills", userBills);
        }

        return "bills";
    }

    @GetMapping("/bills/payment")
    public String paymentPage(@RequestParam("billId") Long billId, Model model) {
        Optional<Bill> bill = billRepository.findById(billId);
        if (bill.isPresent()) {
            model.addAttribute("bill", bill.get());
            return "payment"; // Render the payment.html template
        } else {
            model.addAttribute("error", "Invalid Bill ID.");
            return "redirect:/bills"; // Redirect to bills page if the bill is invalid
        }
    }

    @PostMapping("/bills/payment")
    public String processPayment(@RequestParam("billId") Long billId, Model model) {
        Optional<Bill> bill = billRepository.findById(billId);
        if (bill.isPresent()) {
            Bill existingBill = bill.get();
            existingBill.setStatus("Successful"); // Update the bill status
            billRepository.save(existingBill); // Save the updated bill to the database
            model.addAttribute("message", "Payment successful for Bill ID: " + billId);
        } else {
            model.addAttribute("error", "Invalid Bill ID.");
        }
        return "redirect:/bills"; // Redirect back to the bills page
    }

    @GetMapping("/bills/conflict")
    public String raiseConflict(@RequestParam("billId") Long billId, Model model) {
        Optional<Bill> bill = billRepository.findById(billId);
        if (bill.isPresent()) {
            Bill existingBill = bill.get();
            existingBill.setConflictRaised(true);
            billRepository.save(existingBill);
            model.addAttribute("message", "Conflict raised successfully for Bill ID: " + billId);
        } else {
            model.addAttribute("error", "Invalid Bill ID.");
        }
        return "redirect:/bills";
    }

    @GetMapping("/bills/approve")
    public String approveConflict(@RequestParam("billId") Long billId, HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null || !"ADMIN".equalsIgnoreCase(loggedInUser.getRole())) {
            model.addAttribute("error", "Unauthorized access. Only admins can approve conflicts.");
            return "redirect:/bills";
        }

        Optional<Bill> bill = billRepository.findById(billId);
        if (bill.isPresent()) {
            billRepository.delete(bill.get()); // Remove the bill
            model.addAttribute("message", "Bill removed successfully.");
        } else {
            model.addAttribute("error", "Invalid Bill ID.");
        }
        return "redirect:/bills";
    }

    @GetMapping("/bills/reject")
    public String rejectConflict(@RequestParam("billId") Long billId, HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null || !"ADMIN".equalsIgnoreCase(loggedInUser.getRole())) {
            model.addAttribute("error", "Unauthorized access. Only admins can reject conflicts.");
            return "redirect:/bills";
        }

        Optional<Bill> bill = billRepository.findById(billId);
        if (bill.isPresent()) {
            Bill existingBill = bill.get();
            existingBill.setConflictRaised(false); // Reset conflict
            billRepository.save(existingBill);
            model.addAttribute("message", "Conflict rejected for Bill ID: " + billId);
        } else {
            model.addAttribute("error", "Invalid Bill ID.");
        }
        return "redirect:/bills";
    }
}