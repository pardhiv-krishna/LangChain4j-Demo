package com.demo.langchain4j.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;

/**
 * Configuration class for LangChain4j components with Google Gemini
 * Updated to use latest LangChain4j version with better Gemini support
 */
@Configuration
public class LangChain4jConfig {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.model.name:gemini-2.5-flash}")
    private String modelName;

    /**
     * Configure the Chat Language Model (Google Gemini)
     * Using gemini-2.5-flash which is known to work with LangChain4j
     */
    @Bean
    @Primary
    public ChatLanguageModel chatLanguageModel() {
        return GoogleAiGeminiChatModel.builder()
                .apiKey(geminiApiKey)
                .modelName(modelName)
                .temperature(0.7)
                .timeout(Duration.ofSeconds(60))
                .logRequestsAndResponses(true)
                .build();
    }

    /**
     * Configure the Embedding Model
     * This model converts text into vector embeddings for semantic search
     * Using local all-MiniLM-L6-v2 model (no API key needed)
     */
    @Bean
    public EmbeddingModel embeddingModel() {
        return new AllMiniLmL6V2EmbeddingModel();
    }

    /**
     * Configure the In-Memory Embedding Store
     * This stores document embeddings in RAM for demo/testing purposes
     * Data is lost when application restarts
     */
    @Bean("inMemoryEmbeddingStore")
    public EmbeddingStore<TextSegment> inMemoryEmbeddingStore() {
        return new InMemoryEmbeddingStore<>();
    }

    /**
     * Configure the PgVector Embedding Store
     * This stores document embeddings in PostgreSQL with pgvector extension
     * Data persists across application restarts
     *
     * Prerequisites:
     * 1. PostgreSQL with pgvector extension installed
     * 2. Database created: CREATE DATABASE IF NOT EXISTS rag_demo;
     * 3. Extension enabled: CREATE EXTENSION IF NOT EXISTS vector;
     */
    @Bean("pgVectorEmbeddingStore")
    @Primary
    public EmbeddingStore<TextSegment> pgVectorEmbeddingStore() {
        return PgVectorEmbeddingStore.builder()
                .host("localhost")
                .port(5433)
                .database("rag_demo")
                .user("postgres")
                .password("mysecretpassword")
                .table("document_embeddings")
                .dimension(384)  // Must match embedding model dimension
                .createTable(true)  // Auto-create table if not exists
                .dropTableFirst(false)  // Don't drop existing data
                .build();
    }

    @Bean
    public ContentRetriever contentRetriever(
            EmbeddingStore<TextSegment> embeddingStore, 
            EmbeddingModel embeddingModel) {
            
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore) // Connects to Postgres!
                .embeddingModel(embeddingModel) // Connects to your MiniLM model!
                .maxResults(3)                  // Grab top 3 chunks
                .minScore(0.3)                 
                .build();
    }
}