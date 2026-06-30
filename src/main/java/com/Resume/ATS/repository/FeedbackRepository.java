package com.Resume.ATS.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Resume.ATS.model.FeedbackData;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackData, Long> {

    // Find feedback by resume
    List<FeedbackData> findByResumeId(Long resumeId);

    // Find feedback by resume and job
    Optional<FeedbackData> findByResumeIdAndJobId(Long resumeId, Long jobId);

    // Find feedback by job
    List<FeedbackData> findByJobId(Long jobId);

    // Find feedback for multiple resumes
    List<FeedbackData> findByResumeIdIn(List<Long> resumeIds);

    // User analytics
    List<FeedbackData> findByUserId(Long userId);

    List<FeedbackData> findByUsername(String username);
}