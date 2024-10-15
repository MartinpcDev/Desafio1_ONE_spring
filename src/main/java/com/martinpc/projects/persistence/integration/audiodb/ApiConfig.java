package com.martinpc.projects.persistence.integration.audiodb;

import com.martinpc.projects.persistence.integration.audiodb.service.ConversorData;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiConfig {

  @Value("${integration.api.base-path}")
  private String BASE_URL;

  public <T> T obtenerData(String params, Class<T> clase) {
    ConversorData conversorData = new ConversorData();
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(
            BASE_URL.concat(params != null ? "?" : "").concat(params != null ? params : "")))
        .build();
    HttpResponse<String> response = null;

    try {
      response = client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
    return conversorData.getData(response.body(), clase);
  }
}
