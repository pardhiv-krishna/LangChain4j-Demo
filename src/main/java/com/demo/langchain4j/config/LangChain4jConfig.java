package com.demo.langchain4j.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Configuration class for LangChain4j components with Google Gemini
 * Basic version with in-memory embedding store
 */
@Configuration
public class LangChain4jConfig {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.model.name:gemini-2.5-flash}")
    private String modelName;

    /**
     * Configure the Chat Language Model (Google Gemini)
     * Using gemini-2.5-flash
     */
    @Bean
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
     * Using local all-MiniLM-L6-v2 model (384 dimensions)
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
    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return new InMemoryEmbeddingStore<>();
    }
}