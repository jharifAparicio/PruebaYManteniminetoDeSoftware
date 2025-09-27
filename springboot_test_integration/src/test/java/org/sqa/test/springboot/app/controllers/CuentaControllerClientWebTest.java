package org.sqa.test.springboot.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.sqa.test.springboot.app.models.Cuenta;
import org.sqa.test.springboot.app.models.TransaccionDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CuentaControllerClientWebTest {
    @Autowired
    private WebTestClient client;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        new ObjectMapper();
    }

    @Test
    void testListar(){
        client.get()
                .uri("/api/cuentas")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Cuenta.class)
                .consumeWith(response -> {
                    List<Cuenta> account = response.getResponseBody();
                    assertNotNull(account);
                    assertFalse(account.isEmpty()); // en vez de hacer un zise < que es mas largo y menos entendible
                });
    }

    @Test
    void testDetalleExistente() {
        client.get()
                .uri("/api/cuentas/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1);
    }

    @Test
    void testDetalleInexistente() {
        client.get()
                .uri("/api/cuentas/{id}", 9999)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testGuardar() {
        Cuenta cuenta = new Cuenta();
        // cuenta.setId("123456");
        cuenta.setPersona("Jharif Nelson Aparicio Casillas");
        cuenta.setSaldo(BigDecimal.valueOf(500));

        client.post()
                .uri("/api/cuentas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(cuenta)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.persona").isEqualTo("Jharif Nelson Aparicio Casillas")
                .jsonPath("$.saldo").isEqualTo(BigDecimal.valueOf(500));
    }

    @Test
    void testEliminar() {
        // crear primero
        Cuenta cuenta = new Cuenta();
        cuenta.setPersona("Eliminar");
        cuenta.setSaldo(new BigDecimal("2000"));

        ResponseEntity<Cuenta> creada = restTemplate.postForEntity("http://localhost:" + port + "/api/cuentas", cuenta, Cuenta.class);
        assertEquals(HttpStatus.CREATED, creada.getStatusCode());

        assertNotNull(creada.getBody());
        Long id = creada.getBody().getId();

        restTemplate.delete("http://localhost:" + port + "/api/cuentas/" + id);

        ResponseEntity<Cuenta> eliminada = restTemplate.getForEntity("http://localhost:" + port + "/api/cuentas/" + id, Cuenta.class);
        assertEquals(HttpStatus.NOT_FOUND, eliminada.getStatusCode());
    }

    @Test
    void testTransferirConRestTemplate() throws JsonProcessingException {
        TransaccionDto dto = new TransaccionDto();
        dto.setCuentaOrigenId(1L);
        dto.setCuentaDestinoId(2L);
        dto.setBancoId(1L);
        dto.setMonto(new BigDecimal("100"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TransaccionDto> request = new HttpEntity<>(dto, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/cuentas/transferir", request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        String json = response.getBody();
        assertNotNull(json);
        assertTrue(json.contains("Transferencia realizada con éxito!"));
        assertTrue(json.contains("\"monto\":100"));
    }

    /* @Test
    void transferir() throws JsonProcessingException {
        // Given
        TransaccionDto dto = new TransaccionDto();
        dto.setCuentaOrigenId(1L);
        dto.setCuentaDestinoId(2L);
        dto.setBancoId(1L);
        dto.setMonto(new BigDecimal("100"));

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("mensaje", "Transferencia realizada con éxito!");
        response.put("transaccion", dto);


        // When
        client.post().uri("http://localhost:4050/api/cuentas/transferir")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith( respuesta -> {
                    try {
                        JsonNode json=objectMapper.readTree(respuesta.getResponseBody());
                        assertEquals("Transferencia realizada con éxito!",json.get("mensaje").asText());
                        assertEquals("100",json.path("transaccion").path("monto").asText());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .jsonPath("$.mensaje").isEqualTo("Transferencia realizada con éxito!")
                .jsonPath("$.mensaje").isNotEmpty()
                .json(objectMapper.writeValueAsString(response));
    }
    */
}