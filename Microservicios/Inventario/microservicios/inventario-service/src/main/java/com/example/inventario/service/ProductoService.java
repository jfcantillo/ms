package com.example.inventario.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
@EnableRetry
public class ProductoService {

    private final RestTemplate restTemplate;

    public ProductoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public String getProductoPorId(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", "supersecreta123");
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
            "http://producto-service:8080/producto/" + id,
            HttpMethod.GET,
            request,
            String.class
        );
        return response.getBody();
    }
}