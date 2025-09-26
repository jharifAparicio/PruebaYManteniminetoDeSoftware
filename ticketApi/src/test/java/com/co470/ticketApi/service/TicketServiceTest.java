package com.co470.ticketApi.service;

import com.co470.ticketApi.dao.TicketDao;
import com.co470.ticketApi.entities.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketServiceTest {

    @Mock
    private TicketDao ticketDao;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticket;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ticket = new Ticket();
        ticket.setTicketId(1);
        ticket.setPassengerName("Juan Pérez");
        ticket.setBookingDate(new Date());
        ticket.setSourceStation("Sucre");
        ticket.setDestStation("La Paz");
        ticket.setEmail("juan@example.com");
    }

    @Test
    void testGetAllTickets() {
        when(ticketDao.findAll()).thenReturn(Arrays.asList(ticket));

        Iterable<Ticket> result = ticketService.getAllTickets();

        assertNotNull(result);
        assertEquals("Juan Pérez", result.iterator().next().getPassengerName());
        verify(ticketDao, times(1)).findAll();
    }

    @Test
    void testGetTicketById() {
        when(ticketDao.findById(1)).thenReturn(Optional.of(ticket));

        Ticket result = ticketService.getTicketById(1);

        assertNotNull(result);
        assertEquals("juan@example.com", result.getEmail());
        verify(ticketDao).findById(1);
    }

    @Test
    void testCreateTicket() {
        when(ticketDao.save(ticket)).thenReturn(ticket);

        Ticket result = ticketService.createTicket(ticket);

        assertNotNull(result);
        assertEquals("Sucre", result.getSourceStation());
        verify(ticketDao).save(ticket);
    }

    @Test
    void testDeleteTicket() {
        doNothing().when(ticketDao).deleteById(1);

        ticketService.deleteTicket(1);

        verify(ticketDao, times(1)).deleteById(1);
    }

    @Test
    void testUpdateTicket() {
        when(ticketDao.findById(1)).thenReturn(Optional.of(ticket));
        when(ticketDao.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Ticket updated = ticketService.updateTicket(1, "nuevo@email.com");

        assertNotNull(updated);
        assertEquals("nuevo@email.com", updated.getEmail());
        verify(ticketDao).findById(1);
        verify(ticketDao).save(ticket);
    }
}
