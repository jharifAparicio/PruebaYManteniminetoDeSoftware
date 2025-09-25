package org.sqa.test.springboot.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.sqa.test.springboot.app.models.TransaccionDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CuentaControllerClientWebTest {

    private ObjectMapper objectMapper;
    //
    @Autowired
    private WebTestClient client;
    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();
    }

    @Test
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
}