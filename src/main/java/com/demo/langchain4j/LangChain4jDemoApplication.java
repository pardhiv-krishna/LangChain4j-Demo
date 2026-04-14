package com.demo.langchain4j;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application for LangChain4j Demo
 *
 * This application demonstrates various LangChain4j features:
 * - Basic chat with LLM
 * - RAG (Retrieval Augmented Generation)
 * - Document processing and embeddings
 * - AI Services with structured outputs
 */
@SpringBootApplication
public class LangChain4jDemoApplication {

    public static void main(String[] args) {
        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()  // Don't fail if .env is missing
                .load();
        
        // Set environment variables for Spring Boot
        dotenv.entries().forEach(entry ->
            System.setProperty(entry.getKey(), entry.getValue())
        );
        
        SpringApplication.run(LangChain4jDemoApplication.class, args);
        System.out.println("\n===========================================");
        System.out.println("LangChain4j Application Started!");
        System.out.println("Visit http://localhost:8080");
        System.out.println("===========================================");
    }
}