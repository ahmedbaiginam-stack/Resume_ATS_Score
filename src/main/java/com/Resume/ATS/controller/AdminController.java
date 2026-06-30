package com.Resume.ATS.controller;

import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.Resume.ATS.model.ResumeData;
import com.Resume.ATS.model.FeedbackData;
import com.Resume.ATS.model.User;
import com.Resume.ATS.repository.ResumeRepository;
import com.Resume.ATS.repository.FeedbackRepository;
import com.Resume.ATS.repository.UserRepository;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AdminController {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    // ================= ADMIN DASHBOARD (ALL RESUMES) =================
    @GetMapping("/admin/dashboard")
    public String showDashboard(Model model) {

        List<ResumeData> resumes = resumeRepository.findAll();
        model.addAttribute("resumes", resumes);

        return "admin_dashboard";
    }

    // ================= MY RESUMES (FIXED) =================
    @GetMapping("/admin/resumes")
    public String adminResumes(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String loginId = auth.getName();

        User admin = userRepository.findByEmail(loginId);

        if (admin == null) {
            admin = userRepository.findByUsername(loginId);
        }

        if (admin == null) {
            return "redirect:/admin/dashboard";
        }

        List<ResumeData> resumes = resumeRepository.findByUser(admin);

        model.addAttribute("resumes", resumes);

        return "admin_dashboard";
    }

    // ================= DELETE RESUME =================
    @GetMapping("/admin/delete/{id}")
    public String deleteResume(@PathVariable Long id) {

        List<FeedbackData> feedbacks = feedbackRepository.findByResumeId(id);

        if (feedbacks != null && !feedbacks.isEmpty()) {
            feedbackRepository.deleteAll(feedbacks);
        }

        resumeRepository.deleteById(id);

        return "redirect:/admin/dashboard";
    }

    // ================= SEARCH =================
    @GetMapping("/admin/search")
    public String search(@RequestParam String keyword, Model model) {

        List<ResumeData> results =
                resumeRepository
                        .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrSkillsTextContainingIgnoreCase(
                                keyword, keyword, keyword);

        model.addAttribute("resumes", results);

        return "admin_dashboard";
    }

    // ================= UPDATE FORM =================
    @GetMapping("/admin/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {

        ResumeData resume = resumeRepository.findById(id).orElse(null);

        if (resume == null) {
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("resume", resume);

        return "resume_update_form";
    }

    // ================= UPDATE SUBMIT =================
    @PostMapping("/admin/update/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute ResumeData updated) {

        ResumeData existing = resumeRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setName(updated.getName());
            existing.setEmail(updated.getEmail());
            existing.setPhone(updated.getPhone());
            existing.setSkillsText(updated.getSkillsText());

            resumeRepository.save(existing);
        }

        return "redirect:/admin/dashboard";
    }

    // ================= EXPORT CSV =================
    @GetMapping("/admin/export/csv")
    public void exportCSV(HttpServletResponse response) throws IOException {

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=resumes.csv");

        List<ResumeData> resumes = resumeRepository.findAll();

        CSVPrinter printer = new CSVPrinter(
                response.getWriter(),
                CSVFormat.DEFAULT.withHeader("ID", "Name", "Email", "Skills", "Uploaded At")
        );

        for (ResumeData r : resumes) {
            printer.printRecord(
                    r.getId(),
                    r.getName(),
                    r.getEmail(),
                    r.getSkillsText(),
                    r.getUploadedAt()
            );
        }

        printer.flush();
    }

    // ================= EXPORT PDF =================
    @GetMapping("/admin/export/pdf")
    public void exportPDF(HttpServletResponse response) throws IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=resumes.pdf");

        List<ResumeData> resumes = resumeRepository.findAll();

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        for (ResumeData r : resumes) {
            document.add(new Paragraph("ID: " + r.getId()));
            document.add(new Paragraph("Name: " + r.getName()));
            document.add(new Paragraph("Email: " + r.getEmail()));
            document.add(new Paragraph("Skills: " + r.getSkillsText()));
            document.add(new Paragraph("Uploaded At: " + r.getUploadedAt()));
            document.add(new Paragraph("-----------------------------"));
        }

        document.close();
    }

    // ================= UPLOAD PAGE =================
    @GetMapping("/admin/upload")
    public String uploadPage() {
        return "admin_upload";
    }
}