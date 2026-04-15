# Quick Start Guide

Get the LangChain4j demo running in 5 minutes!

## Prerequisites

- Java 17+
- Maven 3.6+
- Google Gemini API Key (free at https://aistudio.google.com/app/api-keys)

## Steps

### 1. Clone and Setup

```bash
# Clone the repository
git clone <your-repo-url>
cd "LangChain4j Demo"

# Copy environment template
cp .env.example .env
```

### 2. Add Your Gemini API Key

Edit `.env` file:
```bash
GEMINI_API_KEY=your-gemini-api-key-here
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

Wait for:
```
LangChain4j Application Started!
Visit http://localhost:8080
```

### 4. Test It!

Open browser: `http://localhost:8080`

Or test with curl:
```bash
curl http://localhost:8080/api/demo/all
```

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

## Troubleshooting

**"Cannot find GEMINI_API_KEY"**
- Check `.env` file exists and contains your API key
- Restart the application

**"Port 8080 already in use"**
- Change port in `application.properties`: `server.port=8081`

**Compilation errors**
- Ensure Java 17+: `java -version`
- Clean rebuild: `mvn clean install -U`

Happy coding! 🚀