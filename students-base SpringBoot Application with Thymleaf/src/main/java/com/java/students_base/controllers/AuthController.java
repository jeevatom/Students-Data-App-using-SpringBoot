package com.java.students_base.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.students_base.model.User;
import com.java.students_base.service.UserService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        String result = userService.registerUser(user);

        if ("SUCCESS".equals(result)) {
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");
            return "redirect:/login";
        } else if ("USERNAME_EXISTS".equals(result)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Username already exists. Please choose another.");
            return "redirect:/register";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred. Please try again.");
            return "redirect:/register";
        }
    }
}
