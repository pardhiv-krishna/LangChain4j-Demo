package com.demo.langchain4j.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Controller to list available Gemini models
 */
@RestController
@RequestMapping("/api/models")
public class ModelListController {

    @Value("${gemini.api.key:demo}")
    private String geminiApiKey;

    /**
     * List available Gemini models
     * GET /api/models/list
     */
    @GetMapping("/list")
    public ResponseEntity<?> listModels() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://generativelanguage.googleapis.com/v1beta/models?key=" + geminiApiKey;
            
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "error", e.getMessage(),
                "note", "Make sure GEMINI_API_KEY is set correctly",
                "instruction", "Visit https://makersuite.google.com/app/apikey to get your API key"
            ));
        }
    }

    /**
     * Get information about available models
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getModelInfo() {
        return ResponseEntity.ok(Map.of(
            "message", "To list available Gemini models, call GET /api/models/list",
            "note", "Make sure your GEMINI_API_KEY environment variable is set",
            "commonModels", new String[]{
                "gemini-1.5-pro",
                "gemini-1.5-flash", 
                "gemini-1.5-pro-latest",
                "gemini-1.5-flash-latest",
                "gemini-pro",
                "gemini-pro-vision"
            }
        ));
    }
}
