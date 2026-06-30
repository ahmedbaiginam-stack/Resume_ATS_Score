package com.Resume.ATS.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AiFeedbackService {

    private final ChatClient ollamaClient;

    public AiFeedbackService(
            @Qualifier("ollamaClient") ChatClient ollamaClient) {
        this.ollamaClient = ollamaClient;
    }

    public String generateResumeFeedback(String resumeText) {

        return ollamaClient
                .prompt()
                .user("""
                    You are an ATS resume reviewer.
                    Give short, structured feedback.

                    Resume:
                    %s
                    """.formatted(resumeText))
                .call()
                .content();
    }
}