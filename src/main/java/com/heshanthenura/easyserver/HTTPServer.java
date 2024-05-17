package com.heshanthenura.easyserver;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.InetSocketAddress;

public class HTTPServer {
    private static final String DEFAULT_FOLDER_PATH = "E:\\Coding\\Java\\JavaFx\\Projects\\easyserver\\web";

    public static void main(String[] args) throws IOException {
        int port = 8080; // Specify the port number
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // Use the default executor
        server.start();
        System.out.println("Server is listening on port " + port);
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestPath = exchange.getRequestURI().getPath();

            // If the request path ends with "/", it's a directory, so serve index.html
            if (requestPath.endsWith("/")) {
                requestPath += "index.html";
            }

            String filePath = DEFAULT_FOLDER_PATH + requestPath;

            Path path = Paths.get(filePath);

            if (!Files.exists(path)) {
                // If the file doesn't exist, return 404 Not Found
                String response = "404 (Not Found)\n";
                exchange.sendResponseHeaders(404, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                return;
            }

            // Read the file content into a byte array
            byte[] fileContent = Files.readAllBytes(path);

            // Set the response headers
            exchange.sendResponseHeaders(200, fileContent.length);

            // Get the response body output stream
            OutputStream os = exchange.getResponseBody();

            // Write the file content to the output stream
            os.write(fileContent);

            // Close the output stream
            os.close();
        }
    }
}
