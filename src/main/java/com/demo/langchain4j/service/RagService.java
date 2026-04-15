package com.demo.langchain4j.service;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * RAG Service - Manual Implementation
 * Demonstrates the complete RAG pipeline step-by-step:
 * 1. Embed the question
 * 2. Search for relevant documents
 * 3. Build prompt with context
 * 4. Generate response
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RagService {

    private final ChatLanguageModel chatLanguageModel;
    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;

    /**
     * Ingest documents into the embedding store
     * This splits documents into chunks and stores their embeddings
     */
    public void ingestDocuments(List<Document> documents) {
        log.info("Ingesting {} documents", documents.size());

        // Split documents into smaller chunks for better retrieval
        DocumentSplitter splitter = DocumentSplitters.recursive(
                300,  // max chunk size in characters
                50    // overlap between chunks
        );

        for (Document document : documents) {
            List<TextSegment> segments = splitter.split(document);
            log.info("Document split into {} segments", segments.size());

            // Generate embeddings for each segment
            for (TextSegment segment : segments) {
                Embedding embedding = embeddingModel.embed(segment).content();
                embeddingStore.add(embedding, segment);
            }
        }

        log.info("Successfully ingested all documents");
    }

    /**
     * Query using RAG - Manual Implementation
     * Shows each step of the RAG pipeline
     */
    public String query(String question) {
        log.info("RAG query: {}", question);

        // Step 1: Embed the question
        log.info("Step 1: Embedding the question...");
        Embedding questionEmbedding = embeddingModel.embed(question).content();

        // Step 2: Search for relevant documents
        log.info("Step 2: Searching for relevant documents...");
        int maxResults = 3;
        double minScore = 0.3;
        List<EmbeddingMatch<TextSegment>> relevantMatches = embeddingStore.findRelevant(
                questionEmbedding,
                maxResults,
                minScore
        );

        log.info("Found {} relevant documents", relevantMatches.size());

        // Step 3: Extract context from relevant documents
        String context = relevantMatches.stream()
                .map(match -> match.embedded().text())
                .collect(Collectors.joining("\n\n"));

        log.info("Context extracted: {} characters", context.length());

        // Step 4: Build prompt with context
        String prompt = buildPrompt(question, context);
        log.info("Prompt built with context");

        // Step 5: Generate response using LLM
        log.info("Step 5: Generating response...");
        Response<AiMessage> response = chatLanguageModel.generate(UserMessage.from(prompt));
        String answer = response.content().text();

        log.info("RAG response generated: {}", answer);
        return answer;
    }

    /**
     * Build a prompt that includes the context and question
     */
    private String buildPrompt(String question, String context) {
        return String.format("""
                Use the following context to answer the question.
                If the context doesn't contain the answer, say "I don't have enough information to answer that question."
                
                Context:
                %s
                
                Question: %s
                
                Answer:
                """, context, question);
    }

    /**
     * Get statistics about the embedding store
     */
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("status", "active");
        stats.put("embeddingModel", embeddingModel.getClass().getSimpleName());
        stats.put("storeType", embeddingStore.getClass().getSimpleName());
        
        return stats;
    }
}