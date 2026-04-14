package com.demo.langchain4j.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Simple Chat Service demonstrating basic LLM interaction
 * This is the simplest way to use LangChain4j - direct chat with the model
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SimpleChatService {

    private final ChatLanguageModel chatLanguageModel;

    /**
     * Send a simple message to the LLM and get a response
     * 
     * @param userMessage The user's message
     * @return The LLM's response
     */
    public String chat(String userMessage) {
        log.info("Sending message to LLM: {}", userMessage);
        
        String response = chatLanguageModel.generate(userMessage);
        
        log.info("Received response from LLM: {}", response);
        return response;
    }

    /**
     * Chat with system instructions
     * System instructions help guide the model's behavior
     * 
     * @param systemMessage Instructions for the model
     * @param userMessage The user's message
     * @return The LLM's response
     */
    public String chatWithSystemMessage(String systemMessage, String userMessage) {
        log.info("Sending message with system instruction");
        log.info("System: {}", systemMessage);
        log.info("User: {}", userMessage);
        
        String response = chatLanguageModel.generate(
            systemMessage + "\n\nUser: " + userMessage
        );
        
        log.info("Received response: {}", response);
        return response;
    }
}