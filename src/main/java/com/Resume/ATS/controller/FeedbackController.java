package com.Resume.ATS.controller;

import org.springframework.stereotype.Controller;
import java.security.Principal;
import com.Resume.ATS.repository.UserRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.Resume.ATS.repository.*;
import com.Resume.ATS.service.FeedbackService;
import com.Resume.ATS.model.*;
import com.Resume.ATS.model.User;

@Controller
public class FeedbackController {
    private final FeedbackRepository feedbackRepository;
    private final ResumeRepository resumeRepository;
    private final JobDescriptionRepository jobRepo;
    private final FeedbackService feedbackService;
    private final UserRepository userRepository;

    public FeedbackController(FeedbackRepository fr,
            ResumeRepository rr,
            JobDescriptionRepository jr,
            FeedbackService fs,
            UserRepository ur) {

this.feedbackRepository = fr;
this.resumeRepository = rr;
this.jobRepo = jr;
this.feedbackService = fs;
this.userRepository = ur;
}

    // ANALYZE: Leads to Result page
    @GetMapping("/feedback/{resumeId}/{jobId}")
    public String analyzeAndShow(@PathVariable Long resumeId,
                                 @PathVariable Long jobId,
                                 Principal principal,
                                 Model model) {

        System.out.println("==================================");
        System.out.println("Resume ID = " + resumeId);
        System.out.println("Job ID = " + jobId);
        System.out.println("User = " + principal.getName());

        ResumeData resume = resumeRepository.findById(resumeId).orElseThrow();

        System.out.println("Resume Skills = " + resume.getSkills());

        JobDescription job = jobRepo.findById(jobId).orElseThrow();

        System.out.println("Job Skills = " + job.getRequiredSkills());

        User user = userRepository.findByUsername(principal.getName());

        System.out.println("User ID = " + user.getId());

        var result = feedbackService.generateFeedback(
                resume.getSkills(),
                job.getRequiredSkills(),
                resumeId,
                jobId,
                user.getId(),
                user.getUsername());

        System.out.println("Feedback Saved Successfully");
        System.out.println("==================================");

        model.addAttribute("result", result);

        return "feedbackResult";
    }
    
    @GetMapping("/admin/feedback-list")
    public String showAllFeedback(Model model) {
        // Ensure feedbackRepository is not null and correctly injected
        model.addAttribute("feedbackList", feedbackRepository.findAll());
        return "feedback_history"; // Ensure feedback_history.html exists in templates
    }
    // HISTORY: Leads to List page
    @GetMapping("/my-feedback/{resumeId}")
    public String showFeedbackHistory(@PathVariable Long resumeId, Model model) {
        model.addAttribute("feedbackList", feedbackRepository.findByResumeId(resumeId));
        return "feedback_history"; // Must exist as feedback_history.html
    }
}