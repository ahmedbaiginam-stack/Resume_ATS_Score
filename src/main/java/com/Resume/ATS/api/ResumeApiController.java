package com.Resume.ATS.api;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.Resume.ATS.dto.FeedbackResult;
import com.Resume.ATS.model.FeedbackData;
import com.Resume.ATS.model.JobDescription;
import com.Resume.ATS.model.ResumeData;
import com.Resume.ATS.model.User;
import com.Resume.ATS.repository.JobDescriptionRepository;
import com.Resume.ATS.repository.ResumeRepository;
import com.Resume.ATS.repository.UserRepository;
import com.Resume.ATS.service.FeedbackService;
import com.Resume.ATS.service.ResumeParserService;

@RestController
@RequestMapping("/api")
public class ResumeApiController {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private ResumeParserService parserService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private JobDescriptionRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    // ==========================
    // Upload Resume
    // ==========================
    @PostMapping("/upload")
    public ResponseEntity<String> uploadResume(@RequestParam("file") MultipartFile file)
            throws IOException, TikaException {

        if (file.isEmpty() || !file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
            return ResponseEntity.badRequest().body("Only PDF files are allowed.");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(auth.getName());

        if (user == null) {
            throw new RuntimeException("User not found");
        }
        parserService.parseAndSave(file, user);

        return ResponseEntity.ok("Resume uploaded successfully.");
    }

    // ==========================
    // Get All Resumes
    // ==========================
    @GetMapping("/resumes")
    public List<ResumeData> getAllResumes() {
        return resumeRepository.findAll();
    }

    // ==========================
    // Generate ATS Feedback
    // ==========================
    @PostMapping("/feedback")
    public ResponseEntity<FeedbackResult> getFeedback(
            @RequestParam Long resumeId,
            @RequestParam Long jobId) {

        ResumeData resume = resumeRepository.findById(resumeId)
                .orElse(null);

        JobDescription job = jobRepository.findById(jobId)
                .orElse(null);

        if (resume == null || job == null) {
            return ResponseEntity.notFound().build();
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(auth.getName());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        FeedbackResult result = feedbackService.generateFeedback(
                resume.getSkills(),
                job.getRequiredSkills(),
                resumeId,
                jobId,
                user.getId(),
                user.getUsername());

        return ResponseEntity.ok(result);
    }

    // ==========================
    // Get Feedback History
    // ==========================
    @GetMapping("/feedback/{resumeId}")
    public ResponseEntity<List<FeedbackResult>> getFeedbackByResume(@PathVariable Long resumeId) {

        List<FeedbackData> feedbackDataList =
                feedbackService.getFeedbackByResumeId(resumeId);

        if (feedbackDataList == null || feedbackDataList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<FeedbackResult> feedbackList = feedbackDataList.stream()
                .map(data -> {
                    FeedbackResult result = new FeedbackResult();
                    result.setMatchedSkills(data.getMatchedSkills());
                    result.setMissingSkills(data.getMissingSkills());
                    result.setScore((int) data.getScore());
                    result.setFeedback(data.getFeedback());
                    result.setResumeSkills(null);
                    result.setJobRequiredSkills(null);
                    return result;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(feedbackList);
    }
}