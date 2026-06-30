package com.Resume.ATS.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.Resume.ATS.model.JobDescription;
import com.Resume.ATS.repository.JobDescriptionRepository;

@Controller
public class JobDescriptionController {

    @Autowired
    private JobDescriptionRepository jobRepo; // Use only one repository reference

    // ============================
    // Show Jobs (MERGED AND FIXED)
    // ============================
    @GetMapping("/jobs")
    public String showJobs(@RequestParam(value = "resumeId", required = false) Long resumeId, Model model) {
        // Fetch all jobs
        List<JobDescription> jobs = jobRepo.findAll();
        
        // Add data to model
        model.addAttribute("jobs", jobs);
        model.addAttribute("resumeId", resumeId);
        
        System.out.println("Jobs found: " + jobs.size()); // Logging for debugging
        
        return "jobs"; // Ensure you have jobs.html in your templates folder
    }

    // ============================
    // Add Job Page
    // ============================
    @GetMapping("/admin/job/add")
    public String showForm(Model model) {
        model.addAttribute("job", new JobDescription());
        return "add_job";
    }

    // ============================
    // Save Job
    // ============================
    @PostMapping("/admin/job/save")
    public String saveJob(@ModelAttribute JobDescription job,
                          @RequestParam("skills") String skills) {

        List<String> skillList = Arrays.stream(skills.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        job.setRequiredSkills(skillList);
        jobRepo.save(job);

        return "redirect:/admin/dashboard";
    }
}