package com.demo.langchain4j.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Welcome Controller - Provides a landing page for the application
 */
@Controller
public class WelcomeController {

    @GetMapping("/")
    @ResponseBody
    public String welcome() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>LangChain4j Demo Application</title>
                    <style>
                        * {
                            margin: 0;
                            padding: 0;
                            box-sizing: border-box;
                        }
                        body {
                            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
                            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                            color: #333;
                            min-height: 100vh;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            padding: 20px;
                        }
                        .container {
                            background: white;
                            border-radius: 20px;
                            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
                            max-width: 900px;
                            width: 100%;
                            padding: 40px;
                        }
                        h1 {
                            color: #667eea;
                            font-size: 2.5em;
                            margin-bottom: 10px;
                            text-align: center;
                        }
                        .subtitle {
                            text-align: center;
                            color: #666;
                            font-size: 1.1em;
                            margin-bottom: 30px;
                        }
                        .status {
                            background: #10b981;
                            color: white;
                            padding: 10px 20px;
                            border-radius: 25px;
                            display: inline-block;
                            margin-bottom: 30px;
                            font-weight: bold;
                        }
                        .section {
                            margin: 30px 0;
                        }
                        .section h2 {
                            color: #764ba2;
                            font-size: 1.5em;
                            margin-bottom: 15px;
                            border-bottom: 2px solid #667eea;
                            padding-bottom: 10px;
                        }
                        .endpoint {
                            background: #f8f9fa;
                            border-left: 4px solid #667eea;
                            padding: 15px;
                            margin: 10px 0;
                            border-radius: 5px;
                        }
                        .endpoint-method {
                            display: inline-block;
                            background: #667eea;
                            color: white;
                            padding: 3px 10px;
                            border-radius: 3px;
                            font-size: 0.85em;
                            font-weight: bold;
                            margin-right: 10px;
                        }
                        .endpoint-method.get {
                            background: #10b981;
                        }
                        .endpoint-method.post {
                            background: #3b82f6;
                        }
                        .endpoint-path {
                            font-family: 'Courier New', monospace;
                            color: #764ba2;
                            font-weight: bold;
                        }
                        .endpoint-desc {
                            color: #666;
                            margin-top: 5px;
                            font-size: 0.95em;
                        }
                        .quick-test {
                            background: #fef3c7;
                            border: 2px solid #f59e0b;
                            padding: 20px;
                            border-radius: 10px;
                            margin: 20px 0;
                        }
                        .quick-test h3 {
                            color: #f59e0b;
                            margin-bottom: 10px;
                        }
                        .code {
                            background: #1f2937;
                            color: #10b981;
                            padding: 15px;
                            border-radius: 5px;
                            font-family: 'Courier New', monospace;
                            font-size: 0.9em;
                            overflow-x: auto;
                            margin: 10px 0;
                        }
                        .features {
                            display: grid;
                            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
                            gap: 20px;
                            margin: 20px 0;
                        }
                        .feature-card {
                            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                            color: white;
                            padding: 20px;
                            border-radius: 10px;
                            text-align: center;
                        }
                        .feature-card h3 {
                            margin-bottom: 10px;
                            font-size: 1.2em;
                        }
                        .footer {
                            text-align: center;
                            margin-top: 40px;
                            padding-top: 20px;
                            border-top: 1px solid #e5e7eb;
                            color: #666;
                        }
                        a {
                            color: #667eea;
                            text-decoration: none;
                        }
                        a:hover {
                            text-decoration: underline;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>🚀 LangChain4j Demo Application</h1>
                        <p class="subtitle">Demonstrating LLM Integration in Java</p>
                        <div style="text-align: center;">
                            <span class="status">✅ Application Running</span>
                        </div>
                        
                        <div class="section">
                            <h2>📚 Features</h2>
                            <div class="features">
                                <div class="feature-card">
                                    <h3>💬 Simple Chat</h3>
                                    <p>Direct LLM interaction with system messages</p>
                                </div>
                                <div class="feature-card">
                                    <h3>🤖 AI Services</h3>
                                    <p>Type-safe interfaces with automatic implementation</p>
                                </div>
                                <div class="feature-card">
                                    <h3>📖 RAG</h3>
                                    <p>Retrieval Augmented Generation with embeddings</p>
                                </div>
                            </div>
                        </div>
                        
                        <div class="quick-test">
                            <h3>⚡ Quick Test</h3>
                            <p>Run all demos with a single command:</p>
                            <div class="code">curl http://localhost:8080/api/demo/all</div>
                        </div>
                        
                        <div class="section">
                            <h2>🔗 API Endpoints</h2>
                            
                            <h3 style="color: #764ba2; margin-top: 20px;">Demo Endpoints</h3>
                            <div class="endpoint">
                                <span class="endpoint-method get">GET</span>
                                <span class="endpoint-path">/api/demo/all</span>
                                <div class="endpoint-desc">Run all demos in sequence</div>
                            </div>
                            <div class="endpoint">
                                <span class="endpoint-method get">GET</span>
                                <span class="endpoint-path">/api/demo/info</span>
                                <div class="endpoint-desc">Get application information</div>
                            </div>
                            
                            <h3 style="color: #764ba2; margin-top: 20px;">Chat Endpoints</h3>
                            <div class="endpoint">
                                <span class="endpoint-method post">POST</span>
                                <span class="endpoint-path">/api/chat/simple</span>
                                <div class="endpoint-desc">Simple chat with LLM</div>
                            </div>
                            <div class="endpoint">
                                <span class="endpoint-method post">POST</span>
                                <span class="endpoint-path">/api/chat/assistant</span>
                                <div class="endpoint-desc">AI Assistant chat</div>
                            </div>
                            <div class="endpoint">
                                <span class="endpoint-method post">POST</span>
                                <span class="endpoint-path">/api/chat/greet</span>
                                <div class="endpoint-desc">Personalized greeting</div>
                            </div>
                            <div class="endpoint">
                                <span class="endpoint-method post">POST</span>
                                <span class="endpoint-path">/api/chat/translate</span>
                                <div class="endpoint-desc">Translation service</div>
                            </div>
                            
                            <h3 style="color: #764ba2; margin-top: 20px;">RAG Endpoints</h3>
                            <div class="endpoint">
                                <span class="endpoint-method post">POST</span>
                                <span class="endpoint-path">/api/rag/ingest</span>
                                <div class="endpoint-desc">Ingest documents for RAG</div>
                            </div>
                            <div class="endpoint">
                                <span class="endpoint-method post">POST</span>
                                <span class="endpoint-path">/api/rag/query</span>
                                <div class="endpoint-desc">Query with RAG context</div>
                            </div>
                            <div class="endpoint">
                                <span class="endpoint-method get">GET</span>
                                <span class="endpoint-path">/api/rag/stats</span>
                                <div class="endpoint-desc">Get RAG statistics</div>
                            </div>
                        </div>
                        
                        <div class="section">
                            <h2>📖 Documentation</h2>
                            <p>Check out the comprehensive documentation files:</p>
                            <ul style="margin-left: 20px; margin-top: 10px; line-height: 1.8;">
                                <li><strong>README.md</strong> - Complete project documentation</li>
                                <li><strong>QUICKSTART.md</strong> - 5-minute setup guide</li>
                                <li><strong>test-requests.http</strong> - Ready-to-use HTTP test requests</li>
                            </ul>
                        </div>
                        
                        <div class="footer">
                            <p>Built with ❤️ using <a href="https://github.com/langchain4j/langchain4j" target="_blank">LangChain4j</a></p>
                        </div>
                    </div>
                </body>
                </html>
                """;
    }
}