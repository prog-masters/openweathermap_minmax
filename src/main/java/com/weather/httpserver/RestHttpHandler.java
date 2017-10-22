package com.weather.httpserver;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.weather.collector.MinMaxValues;

import java.io.IOException;
import java.io.OutputStream;

public class RestHttpHandler implements HttpHandler {
    private HttpServer httpServer;
    private MinMaxValues minMaxValues;
    private Gson gson = new Gson();

    RestHttpHandler(HttpServer httpServer, MinMaxValues minMaxValues) {
        this.httpServer = httpServer;
        this.minMaxValues = minMaxValues;
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        String response;
        if (t.getRequestURI().toString().equals("/stop")) {
            sendResponse(t, "{\"message\": \"Server is closing...\"}");
            httpServer.stop(1);
            System.out.println("Server stopped.");

        } else {
            sendResponse(t, gson.toJson(minMaxValues));
        }
    }

    private void sendResponse(HttpExchange t, String response) throws IOException {
        t.getResponseHeaders().add("Content-type", "application/json");
        t.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
