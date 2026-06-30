package com.Resume.ATS.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Resume.ATS.model.ResumeData;
import com.Resume.ATS.model.User;
import com.Resume.ATS.repository.ResumeRepository;
import com.Resume.ATS.repository.UserRepository;

@Service
public class ResumeParserService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private UserRepository userRepository;

    // 🔥 IMPORTANT: RETURN OBJECT
    public ResumeData parseAndSave(MultipartFile file, User user)
            throws IOException, TikaException {

        User managedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Tika tika = new Tika();
        String text = tika.parseToString(file.getInputStream());

        ResumeData data = new ResumeData();

        data.setName(extractName(text));
        data.setEmail(extractEmail(text));
        data.setPhone(extractPhone(text));
        data.setSkills(extractSkills(text));
        data.setSkillsText(String.join(", ", extractSkills(text)));
        data.setResumeText(text);
        data.setUser(managedUser);

        // 🔥 SAVE AND RETURN
        return resumeRepository.save(data);
    }

    private String extractName(String text) {
        String[] lines = text.split("\\r?\\n");
        for (String line : lines) {
            if (!line.trim().isEmpty() && line.split("\\s+").length <= 5) {
                return line.trim();
            }
        }
        return "Unknown";
    }

    private String extractEmail(String text) {
        Matcher m = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,6}")
                .matcher(text);
        return m.find() ? m.group() : "unknown@example.com";
    }

    private String extractPhone(String text) {
        Matcher m = Pattern.compile("\\d{10}").matcher(text);
        return m.find() ? m.group() : "0000000000";
    }

    private List<String> extractSkills(String text) {
        String[] skills = {"Java", "Spring Boot", "SQL", "Python", "HTML", "CSS"};
        String lower = text.toLowerCase();

        return Arrays.stream(skills)
                .filter(s -> lower.contains(s.toLowerCase()))
                .toList();
    }
}