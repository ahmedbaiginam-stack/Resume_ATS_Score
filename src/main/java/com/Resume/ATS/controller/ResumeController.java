package com.Resume.ATS.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.Resume.ATS.model.*;
import com.Resume.ATS.repository.JobDescriptionRepository;
import com.Resume.ATS.repository.ResumeRepository;
import com.Resume.ATS.repository.UserRepository;
import com.Resume.ATS.service.ResumeService;

@Controller
public class ResumeController {

    private final ResumeService resumeService;

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }
    @Autowired
    private JobDescriptionRepository jobRepo;

    @GetMapping("/resume/jobs")
    public String showJobs(Model model) {
        model.addAttribute("jobs", jobRepo.findAll());
        return "jobs";
    }
    // ✅ Show resume upload history for the logged-in user
    @GetMapping("/my-resumes")
    public String viewMyResumes(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username);
        List<ResumeData> resumes = resumeRepository.findByUser(user);
        model.addAttribute("resumes", resumes);

        return "my_resumes"; // Must match template name: my_resumes.html
    }
}