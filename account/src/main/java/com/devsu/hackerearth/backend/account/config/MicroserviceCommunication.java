package com.devsu.hackerearth.backend.account.config;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.devsu.hackerearth.backend.account.model.dto.ClientFromMicroserviceDto;
import com.fasterxml.jackson.databind.ObjectMapper;

// For this example, it is sufficient to use an HTTP request, but for production it is better to use RabbitMQ or Kafka.
@Component
public class MicroserviceCommunication {
    private final Logger log = LoggerFactory.getLogger(MicroserviceCommunication.class);

    @Value("${application.microservice.client.address}")
    private String clientMicroserviceAddress;

    @Value("${application.microservice.client.port}")
    private Integer clientMicroservicePort;

    private ObjectMapper mapper;

    public ClientFromMicroserviceDto obtainClientInfo(Long clientId) {
        try {
            String endpoint = new StringBuilder()
                    .append("http://")
                    .append(clientMicroserviceAddress)
                    .append(":")
                    .append(clientMicroservicePort)
                    .append("/api/clients/")
                    .append(clientId)
                    .toString();

            var request = HttpRequest
                    .newBuilder(URI.create(endpoint))
                    .timeout(Duration.ofMillis(500)).GET().build();

            HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), ClientFromMicroserviceDto.class);
            }
            return null;
        } catch (Exception e) {
            log.warn("Error to get client from other microservice: {}", e.getMessage());
            return null;
        }
    }
}
