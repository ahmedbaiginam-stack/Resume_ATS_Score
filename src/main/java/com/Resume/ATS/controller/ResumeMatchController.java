package com.Resume.ATS.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Resume.ATS.dto.SkillMatchResult;
import com.Resume.ATS.model.JobDescription;
import com.Resume.ATS.model.ResumeData;
import com.Resume.ATS.repository.JobDescriptionRepository;
import com.Resume.ATS.repository.ResumeRepository;
import com.Resume.ATS.service.SkillMatchingService;

@Controller
public class ResumeMatchController {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private JobDescriptionRepository jobDescriptionRepository;

    @Autowired
    private SkillMatchingService skillMatchingService;

    @GetMapping("/match/{resumeId}/{jobId}")
    public String matchResumeToJob(@PathVariable Long resumeId,
                                   @PathVariable Long jobId,
                                   Model model) {
        ResumeData resume = resumeRepository.findById(resumeId).orElse(null);
        JobDescription job = jobDescriptionRepository.findById(jobId).orElse(null);

        if (resume == null || job == null) {
            model.addAttribute("error", "Resume or Job not found.");
            return "error";
        }

        SkillMatchResult result = skillMatchingService.matchSkills(resumeId, jobId);

        model.addAttribute("resume", resume);
        model.addAttribute("job", job);
        model.addAttribute("result", result);

        return "match_result";
    }
}