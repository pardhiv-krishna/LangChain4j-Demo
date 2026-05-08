package com.demo.langchain4j.service;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RAG Service - AI Services Pattern
 * Uses ContentRetriever for automatic RAG:
 * - No manual embedding needed
 * - No manual searching needed
 * - No manual prompt building needed
 * ContentRetriever handles everything automatically!
 */
@Service
@Slf4j
public class RagService {

    private final ChatLanguageModel chatLanguageModel;
    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;
    private final ContentRetriever contentRetriever;

    /**
     * RAG Assistant interface
     * The @SystemMessage defines the behavior
     * ContentRetriever automatically provides context
     */
    interface RagAssistant {
        
        @SystemMessage("""
                Use ONLY the information from the context to answer questions.
                If the context doesn't contain the answer, say "I don't have enough information to answer that question."
                Be concise and accurate. Do not make up information.
                """)
        String answer(@UserMessage String question);
    }

    private final RagAssistant ragAssistant;

    public RagService(
            ChatLanguageModel chatLanguageModel,
            EmbeddingModel embeddingModel,
            EmbeddingStore<TextSegment> embeddingStore,
            ContentRetriever contentRetriever
    ) {
        this.chatLanguageModel = chatLanguageModel;
        this.embeddingModel = embeddingModel;
        this.embeddingStore = embeddingStore;
        this.contentRetriever = contentRetriever;
        
        // Create RAG-enabled AI Service
        this.ragAssistant = AiServices.builder(RagAssistant.class)
                .chatLanguageModel(chatLanguageModel)
                .contentRetriever(contentRetriever)
                .build();
        
        log.info("RagService initialized with AI Services pattern");
        log.info("  - Embedding Store: {}", embeddingStore.getClass().getSimpleName());
        log.info("  - Content Retriever: ENABLED (automatic RAG)");
    }

    /**
     * Ingest documents using the Langchain4j EmbeddingStoreIngestor
     */
    public void ingestDocuments(List<Document> documents) {
        log.info("Ingesting {} documents using EmbeddingStoreIngestor", documents.size());

        // Define your chunking strategy
        DocumentSplitter splitter = DocumentSplitters.recursive(300, 50);

        // Build the Ingestor pipeline
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(splitter)
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();

        // Execute the ingestion
        ingestor.ingest(documents);

        log.info("Successfully ingested all documents");
    }

    /**
     * Query using RAG - AUTOMATIC with AI Services!
     * ContentRetriever handles:
     * 1. Embedding the question
     * 2. Searching for relevant documents
     * 3. Providing context to the LLM
     * All in one simple method call!
     */
    public String query(String question) {
        log.info("RAG query: {}", question);
        
        String response = ragAssistant.answer(question);
        
        log.info("RAG response: {}", response);
        return response;
    }

    /**
     * Get statistics about the embedding store
     */
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("status", "active");
        stats.put("embeddingModel", embeddingModel.getClass().getSimpleName());
        stats.put("storeType", embeddingStore.getClass().getSimpleName());
        stats.put("ragPattern", "AI Services with ContentRetriever");
        
        return stats;
    }
}