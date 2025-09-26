package com.co470.ticketApi.controller;

import com.co470.ticketApi.entities.Ticket;
import com.co470.ticketApi.service.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    private Ticket buildTicket() {
        Ticket ticket = new Ticket();
        ticket.setTicketId(1);
        ticket.setPassengerName("Juan Pérez");
        ticket.setBookingDate(new Date());
        ticket.setSourceStation("Sucre");
        ticket.setDestStation("La Paz");
        ticket.setEmail("juan@example.com");
        return ticket;
    }

    @Test
    void testCreateTicket() throws Exception {
        Ticket ticket = buildTicket();
        when(ticketService.createTicket(any(Ticket.class))).thenReturn(ticket);

        mockMvc.perform(post("/api/tickets/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "passengerName":"Juan Pérez",
                          "bookingDate":"2025-09-23",
                          "sourceStation":"Sucre",
                          "destStation":"La Paz",
                          "email":"juan@example.com"
                        }
                        """))
                .andExpect(status().isOk()) // el controller devuelve 200, no 201
                .andExpect(jsonPath("$.passengerName").value("Juan Pérez"));
    }

    @Test
    void testGetTicketById() throws Exception {
        Ticket ticket = buildTicket();
        when(ticketService.getTicketById(1)).thenReturn(ticket);

        mockMvc.perform(get("/api/tickets/ticket/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("juan@example.com"));
    }

    @Test
    void testGetAllTickets() throws Exception {
        Ticket ticket = buildTicket();
        when(ticketService.getAllTickets()).thenReturn(Arrays.asList(ticket));

        mockMvc.perform(get("/api/tickets/ticket/alltickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sourceStation").value("Sucre"));
    }

    @Test
    void testDeleteTicket() throws Exception {
        mockMvc.perform(delete("/api/tickets/ticket/1"))
                .andExpect(status().isOk()); // devuelve 200 aunque sea void
    }

    @Test
    void testUpdateTicket() throws Exception {
        Ticket ticket = buildTicket();
        ticket.setEmail("nuevo@email.com");

        when(ticketService.updateTicket(1, "nuevo@email.com"))
                .thenReturn(ticket);

        mockMvc.perform(put("/api/tickets/ticket/1/nuevo@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("nuevo@email.com"));
    }
}
