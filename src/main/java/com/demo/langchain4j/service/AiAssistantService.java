package com.demo.langchain4j.service;

import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.model.chat.ChatLanguageModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * AI Assistant Service demonstrating AI Services feature
 * AI Services allow you to define interfaces that LangChain4j implements automatically
 * This provides type-safe, structured interactions with LLMs
 */
@Service
@Slf4j
public class AiAssistantService {

    private final Assistant assistant;

    public AiAssistantService(ChatLanguageModel chatLanguageModel) {
        // Create an AI Service implementation
        this.assistant = AiServices.create(Assistant.class, chatLanguageModel);
    }

    /**
     * Chat with the assistant
     */
    public String chat(String message) {
        log.info("Assistant received message: {}", message);
        String response = assistant.chat(message);
        log.info("Assistant response: {}", response);
        return response;
    }

    /**
     * Get a personalized greeting
     */
    public String greet(String name) {
        log.info("Greeting user: {}", name);
        return assistant.greet(name);
    }

    /**
     * Translate text
     */
    public String translate(String text, String targetLanguage) {
        log.info("Translating '{}' to {}", text, targetLanguage);
        return assistant.translate(text, targetLanguage);
    }

    /**
     * AI Assistant Interface
     * LangChain4j will automatically implement this interface
     */
    interface Assistant {

        @SystemMessage("You are a helpful AI assistant. Be concise and friendly.")
        String chat(String userMessage);

        @SystemMessage("You are a friendly greeter.")
        @UserMessage("Greet {{name}} in a warm and professional manner.")
        String greet(@V("name") String name);

        @SystemMessage("You are a professional translator.")
        @UserMessage("Translate the following text to {{language}}: {{text}}")
        String translate(@V("text") String text, @V("language") String targetLanguage);
    }
}