package com.devsu.hackerearth.backend.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.devsu.hackerearth.backend.client.controller.ClientController;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class sampleTest {
    // Test integration between services & controllers
    private ClientService clientService = mock(ClientService.class);
    private ClientController clientController = new ClientController(clientService);

    @Test
    void createClientTest() {
        // Arrange
        ClientDto newClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
        ClientDto createdClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999",
                true);
        when(clientService.create(newClient)).thenReturn(createdClient);

        // Act
        ResponseEntity<ClientDto> response = clientController.create(newClient);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdClient, response.getBody());
    }

    @Test
    void getClientByIdTest() {
        Long clientId = 1L;
        ClientDto client = new ClientDto(clientId, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999",
                true);

        when(clientService.getById(clientId)).thenReturn(client);
        ResponseEntity<ClientDto> response = clientController.get(clientId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(client, response.getBody());
    }

    @Test
    void updateClientTest() {
        // Arrange
        ClientDto updatedClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999",
                false);

        when(clientService.update(updatedClient)).thenReturn(updatedClient);

        // Act
        ResponseEntity<ClientDto> response = clientController.update(1L, updatedClient);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedClient, response.getBody());
    }

    @Test
    void updateClientNotFoundTest() {
        // Arrange
        ClientDto updatedClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999",
                false);

        when(clientService.update(updatedClient)).thenReturn(null);

        // Act
        ResponseEntity<ClientDto> response = clientController.update(1221545L, updatedClient);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @LocalServerPort
    private int port;

    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    private String createEndpoint() {
        return "http://localhost:" + port + "/api/clients";
    }

    private HttpHeaders createJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Test
    @Order(1)
    void createClientIntegrationTest() throws Exception {
        ClientDto newClient = new ClientDto(null, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999",
                true);
        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(newClient), createJsonHeaders());

        ResponseEntity<ClientDto> response = restTemplate.postForEntity(createEndpoint(), request, ClientDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(newClient.getName(), response.getBody().getName());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    @Order(2)
    void getClientIntegrationTest() throws Exception {
        ResponseEntity<ClientDto> response = restTemplate.getForEntity(createEndpoint() + "/" + 1L,
                ClientDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    @Order(3)
    void updateClientIntegrationTest() throws Exception {
        ClientDto toUpdateClient = new ClientDto(1L, "Dni", "DAVID", "MOD Password", "Gender", 1, "Address",
                "9999999999",
                true);
        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(toUpdateClient), createJsonHeaders());
        ResponseEntity<ClientDto> response = restTemplate.exchange(
                createEndpoint() + "/" + 1L,
                HttpMethod.PUT,
                request,
                ClientDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(toUpdateClient.getName(), response.getBody().getName());
        assertEquals(toUpdateClient.getPassword(), response.getBody().getPassword());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    @Order(4)
    void deleteClientIntegrationTest() throws Exception {
        ResponseEntity<Void> response = restTemplate.exchange(
                createEndpoint() + "/" + 1L,
                HttpMethod.DELETE,
                null,
                Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
