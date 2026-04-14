# Quick Start Guide

Get the LangChain4j demo running in 5 minutes!

## Prerequisites

- Java 17+
- Maven 3.6+
- OpenAI API Key

## Steps

### 1. Set Your OpenAI API Key

```bash
export OPENAI_API_KEY=your-api-key-here
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

Wait for the message:
```
🚀 LangChain4j Demo Application Started!
```

### 4. Open in Browser

Visit `http://localhost:8080` in your browser to see the welcome page with all available endpoints.

### 5. Test It!

Open a new terminal and run:

```bash
# Quick test - runs all demos
curl http://localhost:8080/api/demo/all
```

## What's Next?

- Read the full [README.md](README.md) for detailed API documentation
- Check [DEMO_SCRIPT.md](DEMO_SCRIPT.md) for presentation guide
- Try the individual endpoints:
  - Simple Chat: `POST /api/chat/simple`
  - AI Assistant: `POST /api/chat/assistant`
  - RAG Query: `POST /api/rag/query`

## Troubleshooting

**"Cannot find OpenAI API key"**
- Make sure you exported the environment variable
- Or set it in `src/main/resources/application.properties`

**"Port 8080 already in use"**
- Change port in `application.properties`: `server.port=8081`

**Compilation errors**
- Ensure Java 17+ is installed: `java -version`
- Clean and rebuild: `mvn clean install -U`

## Example Requests

### Simple Chat
```bash
curl -X POST http://localhost:8080/api/chat/simple \
  -H "Content-Type: application/json" \
  -d '{"message": "What is Java?"}'
```

### RAG Demo
```bash
# 1. Ingest documents
curl -X POST http://localhost:8080/api/rag/ingest \
  -H "Content-Type: application/json" \
  -d '{"documents": ["Java is a programming language.", "Spring Boot simplifies Java development."]}'

# 2. Query
curl -X POST http://localhost:8080/api/rag/query \
  -H "Content-Type: application/json" \
  -d '{"question": "What is Spring Boot?"}'
```

Happy coding! 🚀