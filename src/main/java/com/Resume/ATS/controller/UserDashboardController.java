package com.Resume.ATS.controller;

import org.springframework.beans.factory.annotation.Autowired;
// CORRECT IMPORT for Authentication
import org.springframework.security.core.Authentication; 
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
// CORRECT IMPORT for UI Model
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Resume.ATS.model.User;
import com.Resume.ATS.repository.ResumeRepository;
import com.Resume.ATS.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserDashboardController {

    @Autowired
    private ResumeRepository resumeRepository;
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String userDashboard(Model model) {
        // Now this will work correctly
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        
        // Fetch only this user's resumes
        model.addAttribute("resumes", resumeRepository.findByUser(user));
        return "user_dashboard";
    }
}