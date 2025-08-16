package com.aluracursos.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {
    public String obtenerDatos(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .header("User-Agent", "Java 11 HttpClient")  // agrega User-Agent
                .GET()
                .build();
        HttpResponse<String> response = null;

            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();
        return json;
    }

}
