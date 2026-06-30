package com.Resume.ATS.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    // Ollama Client (LOCAL AI)
    @Bean(name = "ollamaClient")
    public ChatClient ollamaClient(OllamaChatModel model) {
        return ChatClient.builder(model).build();
    }

    // OPTIONAL: If you are NOT using Gemini, REMOVE geminiClient
    // OR implement it properly if you really use Gemini API
}