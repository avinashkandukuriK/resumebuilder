package com.spring.coverletter.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class logincontroller {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/api/login")
    public String login(String email, String password, Model model) {
        // Validate the email and password (add your own validation logic)
        if (isValidCredentials(email, password)) {
            // Successful login, redirect to a welcome page
            model.addAttribute("username", email);
            return "welcome";
        } else {
            // Invalid credentials, show an error message
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }

    @GetMapping("/googleSignIn")
    public String googleSignIn() {
        // Integrate with Google Sign-In API (add your own logic)
        // Redirect to Google Sign-In page or handle the authentication flow
        return "redirect:/login";
    }

    // Mock validation logic (replace with actual authentication logic)
    private boolean isValidCredentials(String email, String password) {
        // Example: Check against hardcoded credentials (demo purposes)
        return "test@example.com".equals(email) && "password".equals(password);
    }
}
