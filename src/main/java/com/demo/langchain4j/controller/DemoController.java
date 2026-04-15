package com.demo.langchain4j.controller;

import com.demo.langchain4j.service.AiAssistantService;
import com.demo.langchain4j.service.RagService;
import com.demo.langchain4j.service.SimpleChatService;
import dev.langchain4j.data.document.Document;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Demo Controller to showcase all LangChain4j features
 */
@RestController
@RequestMapping("/api/demo")
@RequiredArgsConstructor
public class DemoController {

    private final SimpleChatService simpleChatService;
    private final AiAssistantService aiAssistantService;
    private final RagService ragService;

    /**
     * Run all demos in sequence
     * GET /api/demo/all
     */
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> runAllDemos() {
        Map<String, Object> results = new LinkedHashMap<>();

        try {
            // Demo 1: Simple Chat
            results.put("1_simple_chat", Map.of(
                    "description", "Basic chat with LLM",
                    "input", "What is LangChain4j?",
                    "output", simpleChatService.chat("What is LangChain4j in one sentence?")
            ));

            // Demo 2: Chat with System Message
            results.put("2_system_message", Map.of(
                    "description", "Chat with system instructions",
                    "system", "You are a helpful Java expert",
                    "input", "What is Spring Boot?",
                    "output", simpleChatService.chatWithSystemMessage(
                            "You are a helpful Java expert. Answer in one sentence.",
                            "What is Spring Boot?"
                    )
            ));

            // Demo 3: AI Assistant - Greeting
            results.put("3_ai_assistant_greet", Map.of(
                    "description", "AI Assistant with structured interface",
                    "input", "Alice",
                    "output", aiAssistantService.greet("Alice")
            ));

            // Demo 4: AI Assistant - Translation
            results.put("4_ai_assistant_translate", Map.of(
                    "description", "Translation using AI Assistant",
                    "input", "Hello, how are you?",
                    "language", "Spanish",
                    "output", aiAssistantService.translate("Hello, how are you?", "Spanish")
            ));

            // Demo 5: RAG - Ingest and Query
            // First, ingest some sample documents
            List<Document> sampleDocs = new ArrayList<>();
            sampleDocs.add(Document.from(
                    "LangChain4j is a Java library that makes it easy to integrate Large Language Models (LLMs) " +
                    "into Java applications. It provides abstractions and implementations for common LLM use cases."
            ));
            sampleDocs.add(Document.from(
                    "RAG (Retrieval Augmented Generation) is a technique that combines information retrieval " +
                    "with text generation. It retrieves relevant documents and uses them as context for the LLM."
            ));
            sampleDocs.add(Document.from(
                    "Vector embeddings are numerical representations of text that capture semantic meaning. " +
                    "They enable semantic search by finding similar content based on meaning rather than keywords."
            ));

            ragService.ingestDocuments(sampleDocs);

            String ragQuery = "What is RAG?";
            String ragAnswer = ragService.query(ragQuery);

            results.put("5_rag_demo", Map.of(
                    "description", "RAG - Retrieval Augmented Generation",
                    "documents_ingested", 3,
                    "query", ragQuery,
                    "answer", ragAnswer
            ));

            results.put("status", "success");
            results.put("message", "All demos completed successfully!");

        } catch (Exception e) {
            results.put("status", "error");
            results.put("error", e.getMessage());
            results.put("note", "Make sure GEMINI_API_KEY is set in .env file");
        }

        return ResponseEntity.ok(results);
    }

    /**
     * Get demo information
     * GET /api/demo/info
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getDemoInfo() {
        Map<String, Object> info = new LinkedHashMap<>();
        
        info.put("application", "LangChain4j Demo");
        info.put("description", "Demonstration of LangChain4j features for knowledge sharing");
        
        List<Map<String, String>> features = new ArrayList<>();
        features.add(Map.of(
                "name", "Simple Chat",
                "endpoint", "POST /api/chat/simple",
                "description", "Basic interaction with LLM"
        ));
        features.add(Map.of(
                "name", "AI Assistant",
                "endpoint", "POST /api/chat/assistant",
                "description", "Structured AI services with type-safe interfaces"
        ));
        features.add(Map.of(
                "name", "RAG",
                "endpoint", "POST /api/rag/query",
                "description", "Retrieval Augmented Generation for context-aware responses"
        ));
        features.add(Map.of(
                "name", "Document Ingestion",
                "endpoint", "POST /api/rag/ingest",
                "description", "Ingest documents for RAG"
        ));
        
        info.put("features", features);
        info.put("demo_endpoint", "GET /api/demo/all");
        
        return ResponseEntity.ok(info);
    }
}


