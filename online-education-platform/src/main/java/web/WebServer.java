package web;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import facade.EducationPlatformFacade;
import users.Teacher;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class WebServer {
    private static final EducationPlatformFacade facade = new EducationPlatformFacade();
    private static final int PORT = 8080;
    private static final String WEBAPP_ROOT = "src/main/webapp";
    private HttpServer server;

    public static void main(String[] args) {
        WebServer webServer = new WebServer();
        webServer.start();
    }

    public void start() {
        try {
            server = HttpServer.create(new InetSocketAddress(PORT), 0);
            
            // Static file handler for webapp content
            server.createContext("/", new StaticFileHandler());
            
            // API endpoints
            server.createContext("/api/", new ApiHandler());
            
            server.setExecutor(null);
            server.start();
            
            System.out.println("🌐 Web Server started on http://localhost:" + PORT);

        } catch (IOException e) {
            System.err.println("❌ Failed to start web server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
            System.out.println("🛑 Web server stopped");
        }
    }


    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestPath = exchange.getRequestURI().getPath();
            
            // Default to index.html for root path
            if ("/".equals(requestPath)) {
                requestPath = "/index.html";
            }
            
            // Security: prevent directory traversal
            if (requestPath.contains("..")) {
                sendErrorResponse(exchange, 403, "Forbidden");
                return;
            }
            
            Path filePath = Paths.get(WEBAPP_ROOT + requestPath);
            
            if (!Files.exists(filePath) || Files.isDirectory(filePath)) {
                sendErrorResponse(exchange, 404, "File not found");
                return;
            }
            
            try {
                // Determine content type
                String contentType = getContentType(requestPath);
                
                // Set response headers
                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.getResponseHeaders().set("Cache-Control", "no-cache");
                
                // Send file content
                byte[] fileBytes = Files.readAllBytes(filePath);
                exchange.sendResponseHeaders(200, fileBytes.length);
                
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(fileBytes);
                }
                
            } catch (IOException e) {
                System.err.println("Error serving file: " + filePath + " - " + e.getMessage());
                sendErrorResponse(exchange, 500, "Internal server error");
            }
        }
        
        private String getContentType(String path) {
            if (path.endsWith(".html")) return "text/html; charset=UTF-8";
            if (path.endsWith(".css")) return "text/css";
            if (path.endsWith(".js")) return "application/javascript";
            if (path.endsWith(".json")) return "application/json";
            if (path.endsWith(".png")) return "image/png";
            if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
            if (path.endsWith(".gif")) return "image/gif";
            if (path.endsWith(".svg")) return "image/svg+xml";
            return "text/plain";
        }
        
        private void sendErrorResponse(HttpExchange exchange, int code, String message) throws IOException {
            String response = "<html><body><h1>" + code + " " + message + "</h1></body></html>";
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(code, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }


    static class ApiHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            // Enable CORS
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, Authorization");

            if ("OPTIONS".equals(method)) {
                exchange.sendResponseHeaders(200, -1);
                return;
            }

            try {
                String response = handleApiRequest(method, path, exchange);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.length());

                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }

            } catch (Exception e) {
                System.err.println("API Error: " + e.getMessage());
                String errorResponse = "{\"error\": \"" + e.getMessage() + "\"}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(500, errorResponse.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(errorResponse.getBytes());
                }
            }
        }


        private String handleApiRequest(String method, String path, HttpExchange exchange) throws IOException {
            switch (path) {
                case "/api/students":
                    return handleStudents(method, exchange);
                case "/api/teachers":
                    return handleTeachers(method, exchange);
                case "/api/courses":
                    return handleCourses(method, exchange);
                case "/api/grades":
                    return handleGrades(method, exchange);
                case "/api/login":
                    return handleLogin(method, exchange);
                default:
                    throw new RuntimeException("API endpoint not found: " + path);
            }
        }

        private String handleStudents(String method, HttpExchange exchange) throws IOException {
            if ("GET".equals(method)) {
                return """
        [
          {"id": 1001, "name": "Alice Johnson", "email": "alice.johnson@email.com"},
          {"id": 1002, "name": "Bob Smith", "email": "bob.smith@email.com"}
        ]
        """;
            } else if ("POST".equals(method)) {
                String body = readRequestBody(exchange);
                Map<String, String> data = parseJson(body);

                String name = data.get("name");
                String email = data.get("email");
                int id = Integer.parseInt(data.get("studentId"));

                facade.registerStudent(name, email, id);

                return """
        {"success": true, "message": "Student registered successfully"}
        """;
            }
            return "{}";
        }

        private String handleTeachers(String method, HttpExchange exchange) {
            if ("GET".equals(method)) {
                return """
                [
                    {"id": 1, "name": "Dr. Sarah Wilson", "email": "sarah.wilson@university.edu", "teacherId": "2001", "department": "Computer Science"}
                ]
                """;
            }
            return "{}";
        }

        private String handleCourses(String method, HttpExchange exchange) {
            if ("GET".equals(method)) {
                return """
                [
                    {"id": 1, "name": "Advanced Calculus", "code": "MATH301"},
                    {"id": 2, "name": "Java Programming", "code": "CS201"}
                ]
                """;
            }
            return "{}";
        }

        private String handleGrades(String method, HttpExchange exchange) {
            if ("GET".equals(method)) {
                return """
                [
                    {"studentId": 1, "courseId": 1, "assignment": "Midterm Exam", "score": 88.5, "date": "2024-01-15"},
                    {"studentId": 1, "courseId": 1, "assignment": "Final Exam", "score": 92.0, "date": "2024-02-15"},
                    {"studentId": 1, "courseId": 2, "assignment": "Assignment 1", "score": 85.0, "date": "2024-01-20"},
                    {"studentId": 1, "courseId": 2, "assignment": "Project", "score": 90.0, "date": "2024-02-10"},
                    {"studentId": 2, "courseId": 1, "assignment": "Midterm Exam", "score": 75.0, "date": "2024-01-15"},
                    {"studentId": 2, "courseId": 1, "assignment": "Final Exam", "score": 82.0, "date": "2024-02-15"}
                ]
                """;
            } else if ("POST".equals(method)) {
                // Handle adding new grades
                return "{\"success\": true, \"message\": \"Grade added successfully\"}";
            }
            return "{}";
        }

        private String handleLogin(String method, HttpExchange exchange) throws IOException {
            if ("POST".equals(method)) {
                String requestBody = readRequestBody(exchange);
                Map<String, String> params = parseJson(requestBody);

                String email = params.get("email");
                String password = params.get("password"); // не используется, просто для совместимости

                // Ищем пользователя среди преподавателей и студентов
                for (var entry : facade.getAllTeachers().entrySet()) {
                    Teacher teacher = entry.getValue();  // Получаем Teacher из пары key-value
                    if (teacher.getEmail().equalsIgnoreCase(email)) {
                        return String.format("""
            {"success": true, "userType": "teacher", "user": {"id": %d, "name": "%s", "email": "%s"}}
            """, teacher.getId(), teacher.getName(), teacher.getEmail());
                    }
                }


                return """
        {"success": false, "message": "User not found"}
        """;
            }
            return "{}";
        }
        private Map<String, String> parseJson(String json) {
            Map<String, String> map = new HashMap<>();
            json = json.trim().replaceAll("[{}\"]", "");
            for (String pair : json.split(",")) {
                String[] kv = pair.split(":");
                if (kv.length == 2) {
                    map.put(kv[0].trim(), kv[1].trim());
                }
            }
            return map;
        }

        private String readRequestBody(HttpExchange exchange) throws IOException {
            try (InputStream is = exchange.getRequestBody()) {
                return new String(is.readAllBytes());
            }
        }
    }
}
