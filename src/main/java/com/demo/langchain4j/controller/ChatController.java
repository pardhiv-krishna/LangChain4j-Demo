package com.demo.langchain4j.controller;

import com.demo.langchain4j.service.AiAssistantService;
import com.demo.langchain4j.service.SimpleChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST Controller for chat endpoints
 */
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final SimpleChatService simpleChatService;
    private final AiAssistantService aiAssistantService;

    /**
     * Simple chat endpoint
     * POST /api/chat/simple
     * Body: { "message": "your message here" }
     */
    @PostMapping("/simple")
    public ResponseEntity<Map<String, String>> simpleChat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        String response = simpleChatService.chat(message);
        return ResponseEntity.ok(Map.of("response", response));
    }

    /**
     * Chat with system message
     * POST /api/chat/system
     * Body: { "systemMessage": "...", "userMessage": "..." }
     */
    @PostMapping("/system")
    public ResponseEntity<Map<String, String>> chatWithSystem(@RequestBody Map<String, String> request) {
        String systemMessage = request.get("systemMessage");
        String userMessage = request.get("userMessage");
        String response = simpleChatService.chatWithSystemMessage(systemMessage, userMessage);
        return ResponseEntity.ok(Map.of("response", response));
    }

    /**
     * AI Assistant chat
     * POST /api/chat/assistant
     * Body: { "message": "your message here" }
     */
    @PostMapping("/assistant")
    public ResponseEntity<Map<String, String>> assistantChat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        String response = aiAssistantService.chat(message);
        return ResponseEntity.ok(Map.of("response", response));
    }

    /**
     * Greet endpoint
     * POST /api/chat/greet
     * Body: { "name": "John" }
     */
    @PostMapping("/greet")
    public ResponseEntity<Map<String, String>> greet(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String response = aiAssistantService.greet(name);
        return ResponseEntity.ok(Map.of("response", response));
    }

    /**
     * Translate endpoint
     * POST /api/chat/translate
     * Body: { "text": "Hello", "language": "Spanish" }
     */
    @PostMapping("/translate")
    public ResponseEntity<Map<String, String>> translate(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        String language = request.get("language");
        String response = aiAssistantService.translate(text, language);
        return ResponseEntity.ok(Map.of("response", response));
    }
}