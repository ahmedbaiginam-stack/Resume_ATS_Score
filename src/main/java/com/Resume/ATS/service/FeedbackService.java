package com.Resume.ATS.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Resume.ATS.dto.FeedbackResult;
import com.Resume.ATS.model.FeedbackData;
import com.Resume.ATS.model.ResumeData;
import com.Resume.ATS.repository.FeedbackRepository;
import com.Resume.ATS.repository.ResumeRepository;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ResumeRepository resumeRepository;
    private final AiFeedbackService aiFeedbackService;

    public FeedbackService(FeedbackRepository feedbackRepository,
                           ResumeRepository resumeRepository,
                           AiFeedbackService aiFeedbackService) {

        this.feedbackRepository = feedbackRepository;
        this.resumeRepository = resumeRepository;
        this.aiFeedbackService = aiFeedbackService;
    }

    private static final Map<String, Integer> skillWeights = Map.ofEntries(
            Map.entry("java", 4),
            Map.entry("spring boot", 3),
            Map.entry("sql", 3),
            Map.entry("docker", 2),
            Map.entry("aws", 3),
            Map.entry("python", 2),
            Map.entry("html", 1),
            Map.entry("css", 1)
    );

    // ================== AI Feedback + Save ==================

    public FeedbackData processAndSave(Long resumeId,
                                       Long jobId,
                                       List<String> resumeSkills,
                                       List<String> requiredSkills) {

        FeedbackResult result = computeMatchResult(resumeSkills, requiredSkills);

        String prompt = """
                Analyze this resume-job match.

                ATS Score: %d%%

                Matched Skills:
                %s

                Missing Skills:
                %s

                Give professional improvement suggestions in 5 points.
                """
                .formatted(result.getScore(),
                        result.getMatchedSkills(),
                        result.getMissingSkills());

        String aiFeedback = aiFeedbackService.generateResumeFeedback(prompt);

        FeedbackData data = feedbackRepository
                .findByResumeIdAndJobId(resumeId, jobId)
                .orElse(new FeedbackData());

        data.setResumeId(resumeId);
        data.setJobId(jobId);
        data.setScore(result.getScore());
        data.setMatchedSkills(result.getMatchedSkills());
        data.setMissingSkills(result.getMissingSkills());
        data.setFeedback(aiFeedback);

        // Save logged-in user
        ResumeData resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        data.setUserId(resume.getUser().getId());
        data.setUsername(resume.getUser().getUsername());

        return feedbackRepository.save(data);
    }

    // ================== Normal Feedback ==================

    public FeedbackResult generateFeedback(List<String> resumeSkills,
                                           List<String> requiredSkills,
                                           Long resumeId,
                                           Long jobId,
                                           Long userId,
                                           String username){

        FeedbackResult result = computeMatchResult(resumeSkills, requiredSkills);

        FeedbackData feedbackData = feedbackRepository
                .findByResumeIdAndJobId(resumeId, jobId)
                .orElse(new FeedbackData());

        feedbackData.setResumeId(resumeId);
        feedbackData.setJobId(jobId);
        feedbackData.setUserId(userId);
        feedbackData.setUsername(username);
        feedbackData.setScore(result.getScore());
        feedbackData.setFeedback(result.getFeedback());
        feedbackData.setMatchedSkills(result.getMatchedSkills());
        feedbackData.setMissingSkills(result.getMissingSkills());

        // Save logged-in user
        ResumeData resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        feedbackData.setUserId(resume.getUser().getId());
        feedbackData.setUsername(resume.getUser().getUsername());

        feedbackRepository.save(feedbackData);

        return result;
    }

    // ================== ATS Matching ==================

    public FeedbackResult computeMatchResult(List<String> resumeSkills,
                                             List<String> requiredSkills) {

        if (resumeSkills == null)
            resumeSkills = new ArrayList<>();

        if (requiredSkills == null)
            requiredSkills = new ArrayList<>();

        Set<String> resumeSet = resumeSkills.stream()
                .map(s -> s.trim().toLowerCase())
                .collect(Collectors.toSet());

        Set<String> requiredSet = requiredSkills.stream()
                .map(s -> s.trim().toLowerCase())
                .collect(Collectors.toSet());

        Set<String> matched = new HashSet<>(resumeSet);
        matched.retainAll(requiredSet);

        Set<String> missing = new HashSet<>(requiredSet);
        missing.removeAll(resumeSet);

        int totalWeight = requiredSet.stream()
                .mapToInt(skill -> skillWeights.getOrDefault(skill, 1))
                .sum();

        int matchedWeight = matched.stream()
                .mapToInt(skill -> skillWeights.getOrDefault(skill, 1))
                .sum();

        int score = totalWeight == 0
                ? 0
                : (int) (((double) matchedWeight / totalWeight) * 100);

        String feedback;

        if (score >= 80) {
            feedback = "Excellent match!";
        } else if (score >= 60) {
            feedback = "Good match. Consider learning: " + String.join(", ", missing);
        } else if (score >= 40) {
            feedback = "Fair match. Improve these: " + String.join(", ", missing);
        } else {
            feedback = "Low match. Learn key skills: " + String.join(", ", missing);
        }

        FeedbackResult result = new FeedbackResult();
        result.setResumeSkills(new ArrayList<>(resumeSet));
        result.setJobRequiredSkills(new ArrayList<>(requiredSet));
        result.setMatchedSkills(new ArrayList<>(matched));
        result.setMissingSkills(new ArrayList<>(missing));
        result.setScore(score);
        result.setFeedback(feedback);

        return result;
    }

    // ================== History ==================

    public List<FeedbackData> getFeedbackByResumeId(Long resumeId) {
        return feedbackRepository.findByResumeId(resumeId);
    }

}