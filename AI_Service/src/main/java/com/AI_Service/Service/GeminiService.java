package com.AI_Service.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * Service to communicate with Google Gemini AI.
 * If Gemini API is available, it returns AI-generated advice.
 * If API key is not configured or network fails, it safely returns null
 * so our services can use built-in smart agricultural rules.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiService {

    private final ChatClient chatClient;

    public String ask(String prompt) {
        try {
            log.info("Sending prompt to Gemini AI...");
            String response = chatClient
                    .prompt()
                    .user(prompt)
                    .call()
                    .content();

            if (response != null && !response.isBlank()) {
                return response;
            }
        } catch (Exception e) {
            log.warn("Gemini AI API call failed or key not set. Using smart fallback advisory: {}", e.getMessage());
        }
        return null;
    }
}