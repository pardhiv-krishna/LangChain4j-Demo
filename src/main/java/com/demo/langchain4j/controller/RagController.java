package com.demo.langchain4j.controller;

import com.demo.langchain4j.service.RagService;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for RAG (Retrieval Augmented Generation) endpoints
 */
@RestController
@RequestMapping("/api/rag")
@RequiredArgsConstructor
public class RagController {

    private final RagService ragService;

    /**
     * Ingest text documents into the RAG system
     * POST /api/rag/ingest
     * Body: { "documents": ["text1", "text2", ...] }
     */
    @PostMapping("/ingest")
    public ResponseEntity<Map<String, String>> ingestDocuments(@RequestBody Map<String, List<String>> request) {
        List<String> texts = request.get("documents");
        
        if (texts == null || texts.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "No documents provided"));
        }

        List<Document> documents = new ArrayList<>();
        for (String text : texts) {
            documents.add(Document.from(text));
        }

        ragService.ingestDocuments(documents);

        return ResponseEntity.ok(Map.of(
                "message", "Successfully ingested " + documents.size() + " documents",
                "count", String.valueOf(documents.size())
        ));
    }

    /**
     * Query the RAG system
     * POST /api/rag/query
     * Body: { "question": "your question here" }
     */
    @PostMapping("/query")
    public ResponseEntity<Map<String, String>> query(@RequestBody Map<String, String> request) {
        String question = request.get("question");
        
        if (question == null || question.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "No question provided"));
        }

        String answer = ragService.query(question);

        return ResponseEntity.ok(Map.of(
                "question", question,
                "answer", answer
        ));
    }

    /**
     * Get RAG system statistics
     * GET /api/rag/stats
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = ragService.getStats();
        return ResponseEntity.ok(stats);
    }
}