# LangChain4j Demo Application

A comprehensive demonstration application showcasing the key features of [LangChain4j](https://github.com/langchain4j/langchain4j) - a Java library for integrating Large Language Models (LLMs) into Java applications.

## 🎯 Purpose

This project is designed for knowledge sharing sessions to demonstrate:
- Basic LLM chat interactions
- AI Services with structured interfaces
- RAG (Retrieval Augmented Generation)
- Document processing and embeddings
- Vector search and semantic retrieval

## 🚀 Features

### 1. Simple Chat
Basic interaction with LLMs using direct API calls.
- Direct message-response pattern
- System message configuration
- Temperature and model settings

### 2. AI Assistant Services
Type-safe, structured interactions using Java interfaces.
- Automatic implementation by LangChain4j
- `@SystemMessage` and `@UserMessage` annotations
- Variable interpolation with `@V`
- Examples: greeting, translation

### 3. RAG (Retrieval Augmented Generation)
Context-aware responses using document embeddings.
- Document ingestion and chunking
- Vector embeddings (using all-MiniLM-L6-v2)
- Semantic search
- Context-enhanced responses

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Gemini API key (for LLM access)

## 🔧 Setup

### 1. Clone or Navigate to Project Directory
```bash
cd /Users/pardhivkrishna/JUG
```

### 2. Set OpenAI API Key

**Option A: Environment Variable (Recommended)**
```bash
export OPENAI_API_KEY=your-api-key-here
```

**Option B: Application Properties**
Edit `src/main/resources/application.properties`:
```properties
openai.api.key=your-api-key-here
```

### 3. Build the Project
```bash
mvn clean install
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

**Access the application:**
- Open your browser and visit: `http://localhost:8080`
- You'll see a beautiful welcome page with all available endpoints

## 📚 API Endpoints

### Demo Endpoints

#### Run All Demos
```bash
GET http://localhost:8080/api/demo/all
```
Executes all demo features in sequence and returns results.

#### Get Demo Info
```bash
GET http://localhost:8080/api/demo/info
```
Returns information about available features and endpoints.

### Chat Endpoints

#### Simple Chat
```bash
POST http://localhost:8080/api/chat/simple
Content-Type: application/json

{
  "message": "What is LangChain4j?"
}
```

#### Chat with System Message
```bash
POST http://localhost:8080/api/chat/system
Content-Type: application/json

{
  "systemMessage": "You are a helpful Java expert",
  "userMessage": "Explain Spring Boot"
}
```

#### AI Assistant Chat
```bash
POST http://localhost:8080/api/chat/assistant
Content-Type: application/json

{
  "message": "Tell me about Java"
}
```

#### Greet
```bash
POST http://localhost:8080/api/chat/greet
Content-Type: application/json

{
  "name": "Alice"
}
```

#### Translate
```bash
POST http://localhost:8080/api/chat/translate
Content-Type: application/json

{
  "text": "Hello, how are you?",
  "language": "Spanish"
}
```

### RAG Endpoints

#### Ingest Documents
```bash
POST http://localhost:8080/api/rag/ingest
Content-Type: application/json

{
  "documents": [
    "LangChain4j is a Java library for LLM integration...",
    "RAG combines retrieval with generation...",
    "Vector embeddings capture semantic meaning..."
  ]
}
```

#### Query with RAG
```bash
POST http://localhost:8080/api/rag/query
Content-Type: application/json

{
  "question": "What is RAG?"
}
```

#### Get RAG Stats
```bash
GET http://localhost:8080/api/rag/stats
```

## 🧪 Testing with cURL

### Quick Test - Run All Demos
```bash
curl http://localhost:8080/api/demo/all
```

### Simple Chat Test
```bash
curl -X POST http://localhost:8080/api/chat/simple \
  -H "Content-Type: application/json" \
  -d '{"message": "What is Java?"}'
```

### RAG Test
```bash
# 1. Ingest documents
curl -X POST http://localhost:8080/api/rag/ingest \
  -H "Content-Type: application/json" \
  -d '{
    "documents": [
      "Spring Boot is a framework that simplifies Java application development.",
      "Maven is a build automation tool for Java projects."
    ]
  }'

# 2. Query
curl -X POST http://localhost:8080/api/rag/query \
  -H "Content-Type: application/json" \
  -d '{"question": "What is Spring Boot?"}'
```

## 🏗️ Project Structure

```
langchain4j-demo/
├── src/main/java/com/demo/langchain4j/
│   ├── LangChain4jDemoApplication.java    # Main application
│   ├── config/
│   │   └── LangChain4jConfig.java         # LangChain4j configuration
│   ├── controller/
│   │   ├── ChatController.java            # Chat endpoints
│   │   ├── RagController.java             # RAG endpoints
│   │   └── DemoController.java            # Demo endpoints
│   └── service/
│       ├── SimpleChatService.java         # Basic chat service
│       ├── AiAssistantService.java        # AI Services demo
│       └── RagService.java                # RAG implementation
├── src/main/resources/
│   └── application.properties             # Configuration
├── pom.xml                                # Maven dependencies
└── README.md                              # This file
```

## 🔑 Key Concepts Demonstrated

### 1. ChatLanguageModel
The core interface for interacting with LLMs:
```java
ChatLanguageModel model = OpenAiChatModel.builder()
    .apiKey(apiKey)
    .modelName("gpt-3.5-turbo")
    .build();

String response = model.generate("Your message");
```

### 2. AI Services
Define interfaces that LangChain4j implements:
```java
interface Assistant {
    @SystemMessage("You are a helpful assistant")
    String chat(String message);
    
    @UserMessage("Translate {{text}} to {{language}}")
    String translate(@V("text") String text, @V("language") String lang);
}

Assistant assistant = AiServices.create(Assistant.class, model);
```

### 3. RAG Pipeline
1. **Document Ingestion**: Split documents into chunks
2. **Embedding**: Convert text to vectors
3. **Storage**: Store in vector database
4. **Retrieval**: Find relevant chunks for query
5. **Generation**: Use retrieved context to generate response

### 4. Embeddings
Convert text to numerical vectors for semantic search:
```java
EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();
Embedding embedding = embeddingModel.embed("Your text").content();
```

## 📖 Learning Resources

- [LangChain4j Documentation](https://docs.langchain4j.dev/)
- [LangChain4j GitHub](https://github.com/langchain4j/langchain4j)
- [OpenAI API Documentation](https://platform.openai.com/docs)

## 🎓 Knowledge Sharing Tips

When presenting this demo:

1. **Start Simple**: Begin with SimpleChatService to show basic LLM interaction
2. **Show Structure**: Demonstrate AI Services for type-safe interfaces
3. **Explain RAG**: Walk through the RAG pipeline step-by-step
4. **Live Demo**: Use the `/api/demo/all` endpoint for a complete walkthrough
5. **Interactive**: Let attendees try different queries and see responses

## 🛠️ Troubleshooting

### Lombok Errors
If you see compilation errors related to Lombok:
```bash
mvn clean install -U
```

### OpenAI API Errors
- Verify your API key is set correctly
- Check your OpenAI account has credits
- Ensure you're using a valid model name

### Port Already in Use
Change the port in `application.properties`:
```properties
server.port=8081
```

## 📝 Notes

- The application uses an in-memory embedding store (data is lost on restart)
- For production, use a persistent vector database (Pinecone, Weaviate, etc.)
- The local embedding model (all-MiniLM-L6-v2) runs without API calls
- Adjust temperature and other model parameters in `LangChain4jConfig.java`

## 🤝 Contributing

This is a demo project for knowledge sharing. Feel free to:
- Add more examples
- Improve documentation
- Add tests
- Enhance error handling

## 📄 License

This project is for educational purposes.

---

**Happy Learning! 🚀**

For questions or issues, please refer to the [LangChain4j documentation](https://docs.langchain4j.dev/).