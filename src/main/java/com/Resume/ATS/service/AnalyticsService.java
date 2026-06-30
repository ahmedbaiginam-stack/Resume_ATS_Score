package com.Resume.ATS.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Resume.ATS.dto.AnalyticsDTO;
import com.Resume.ATS.model.FeedbackData;
import com.Resume.ATS.repository.FeedbackRepository;

@Service
public class AnalyticsService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    // --- FOR ADMIN: ALL DATA ---
    public AnalyticsDTO getAnalytics() {
        return processAnalytics(feedbackRepository.findAll());
    }

    // --- FOR USER: FILTERED BY USER ID ---
    public AnalyticsDTO getAnalyticsForUser(Long userId) {
        return processAnalytics(feedbackRepository.findByUserId(userId));
    }

    // --- CORE LOGIC: SHARED BY BOTH ---
    private AnalyticsDTO processAnalytics(List<FeedbackData> feedbackList) {
        // Handle case with no data to prevent NullPointerExceptions
        if (feedbackList == null || feedbackList.isEmpty()) {
            return new AnalyticsDTO(0, 0.0, new LinkedHashMap<>(), new LinkedHashMap<>());
        }

        double avgScore = feedbackList.stream()
                .mapToDouble(FeedbackData::getScore)
                .average()
                .orElse(0.0);

        Map<String, Integer> matchedFreq = new HashMap<>();
        Map<String, Integer> missingFreq = new HashMap<>();

        for (FeedbackData feedback : feedbackList) {
            // Process Matched Skills
            if (feedback.getMatchedSkills() != null) {
                for (String skill : feedback.getMatchedSkills()) {
                    String clean = skill.trim().toLowerCase();
                    if (!clean.isEmpty()) 
                        matchedFreq.put(clean, matchedFreq.getOrDefault(clean, 0) + 1);
                }
            }
            // Process Missing Skills
            if (feedback.getMissingSkills() != null) {
                for (String skill : feedback.getMissingSkills()) {
                    String clean = skill.trim().toLowerCase();
                    if (!clean.isEmpty()) 
                        missingFreq.put(clean, missingFreq.getOrDefault(clean, 0) + 1);
                }
            }
        }

        return new AnalyticsDTO(
                feedbackList.size(),
                Math.round(avgScore * 100.0) / 100.0,
                sortAndLimit(matchedFreq, 5),
                sortAndLimit(missingFreq, 5)
        );
    }

    // Helper: Sorts map by value (descending) and limits to top N
    private Map<String, Integer> sortAndLimit(Map<String, Integer> input, int limit) {
        return input.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }
}