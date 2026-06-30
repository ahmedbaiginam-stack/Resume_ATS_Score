package com.Resume.ATS.controller;

import java.security.Principal;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.Resume.ATS.dto.AnalyticsDTO;
import com.Resume.ATS.model.FeedbackData;
import com.Resume.ATS.repository.FeedbackRepository;

@Controller
public class AnalyticsController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    // --- ADMIN ANALYTICS ---
    @GetMapping("/admin/analytics")
    public String showAnalytics(Model model) {
        List<FeedbackData> allFeedback = feedbackRepository.findAll();
        System.out.println("Admin Analytics: Total records found: " + allFeedback.size());
        
        model.addAttribute("analytics", processAnalytics(allFeedback));
        return "admin_analytics";
    }

    // --- USER ANALYTICS ---
    @GetMapping("/user/analytics")
    public String showUserAnalytics(Principal principal, Model model) {
        if (principal == null) return "redirect:/login"; // Prevent NPE
        
        String username = principal.getName();
        // Ensure this matches your Repository exactly
        List<FeedbackData> userFeedback = feedbackRepository.findByUsername(username);
        
        // Safety check
        if (userFeedback == null) {
            userFeedback = new ArrayList<>();
        }
        
        model.addAttribute("analytics", processAnalytics(userFeedback));
        return "user_analytics";
    }

    // --- SHARED DATA PROCESSING LOGIC ---
    private AnalyticsDTO processAnalytics(List<FeedbackData> feedbackList) {
        if (feedbackList == null || feedbackList.isEmpty()) {
            return new AnalyticsDTO(0, 0.0, new LinkedHashMap<>(), new LinkedHashMap<>());
        }

        double avgScore = feedbackList.stream()
                .mapToDouble(FeedbackData::getScore)
                .average().orElse(0.0);

        Map<String, Integer> matchedSkillFreq = new HashMap<>();
        Map<String, Integer> missingSkillFreq = new HashMap<>();

        for (FeedbackData feedback : feedbackList) {
            // Process Matched Skills
            if (feedback.getMatchedSkills() != null) {
                for (String skill : feedback.getMatchedSkills()) {
                    String clean = skill.trim().toLowerCase();
                    if (!clean.isEmpty()) 
                        matchedSkillFreq.put(clean, matchedSkillFreq.getOrDefault(clean, 0) + 1);
                }
            }
            // Process Missing Skills
            if (feedback.getMissingSkills() != null) {
                for (String skill : feedback.getMissingSkills()) {
                    String clean = skill.trim().toLowerCase();
                    if (!clean.isEmpty()) 
                        missingSkillFreq.put(clean, missingSkillFreq.getOrDefault(clean, 0) + 1);
                }
            }
        }

        return new AnalyticsDTO(
                feedbackList.size(),
                Math.round(avgScore * 100.0) / 100.0,
                getTopEntries(matchedSkillFreq, 5),
                getTopEntries(missingSkillFreq, 5)
        );
    }

    // Helper: Sort and limit map
    private Map<String, Integer> getTopEntries(Map<String, Integer> map, int limit) {
        return map.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(limit)
                .collect(LinkedHashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);
    }
}