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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RAG Service - AI Services Pattern
 * Key features:
 * - Uses AI Services pattern with interface
 * - Automatic RAG with ContentRetriever
 * - System message defines behavior
 * - No manual prompt building needed!
 */
@Service
@Slf4j
public class RagService {

    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;
    private final RagAssistant ragAssistant;

    interface RagAssistant {
        
        @SystemMessage("""
                Use ONLY the information from the context to answer questions.
                If the context doesn't contain the answer, say "I don't have enough information to answer that question."
                Be concise and accurate. Do not make up information
                """)
        String answer(@UserMessage String question);
    }

    /**
     * Constructor - Creates RAG-enabled AI Service
     *
     * @param chatLanguageModel The LLM for generating responses
     * @param embeddingModel The model for creating embeddings
     * @param embeddingStore The vector store (pgVectorEmbeddingStore or inMemoryEmbeddingStore)
     * @param contentRetriever The retriever that handles embedding + search automatically
     */
    public RagService(
            ChatLanguageModel chatLanguageModel,
            EmbeddingModel embeddingModel,
            @Qualifier("pgVectorEmbeddingStore") EmbeddingStore<TextSegment> embeddingStore,
            ContentRetriever contentRetriever
    ) {
        this.embeddingModel = embeddingModel;
        this.embeddingStore = embeddingStore;
        
        // Create RAG-enabled AI Service
        this.ragAssistant = AiServices.builder(RagAssistant.class)
                .chatLanguageModel(chatLanguageModel)
                .contentRetriever(contentRetriever)  // This enables automatic RAG!
                .build();
        
        log.info("RagService initialized with:");
        log.info("  - Embedding Store: {}", embeddingStore.getClass().getSimpleName());
        log.info("  - Content Retriever: {}", contentRetriever.getClass().getSimpleName());
        log.info("  - RAG Assistant: ENABLED (automatic retrieval + generation)");
    }

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
     * Query using RAG - AUTOMATIC with AI Services!
     * No manual prompt building needed!
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
        
        return stats;
    }
}