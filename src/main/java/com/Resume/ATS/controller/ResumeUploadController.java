package com.Resume.ATS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Resume.ATS.model.ResumeData;
import com.Resume.ATS.model.User;
import com.Resume.ATS.repository.UserRepository;
import com.Resume.ATS.service.ResumeParserService;

@Controller
public class ResumeUploadController {

    @Autowired
    private ResumeParserService parserService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/upload")
    public String uploadPage() {
        return "upload";
    }

    @PostMapping("/upload")
    public String handleUpload(@RequestParam("file") MultipartFile file,
                               Model model) {

        try {
            if (file.isEmpty()) {
                model.addAttribute("error", "Please select a file");
                return "upload";
            }

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userRepository.findByUsername(auth.getName());

            if (user == null) {
                return "redirect:/login";
            }

            // 🔥 SAVE + GET RESUME OBJECT
            ResumeData resume = parserService.parseAndSave(file, user);

            // 🔥 AUTO REDIRECT TO JOBS (feedback trigger)
            return "redirect:/jobs?resumeId=" + resume.getId();

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Upload failed");
            return "upload";
        }
    }
}