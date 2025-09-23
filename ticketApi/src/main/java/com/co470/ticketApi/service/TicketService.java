package com.co470.ticketApi.service;

import com.co470.ticketApi.dao.TicketDao;
import com.co470.ticketApi.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketService {
   @Autowired (required = true)
   TicketDao ticketDao;

    public TicketService(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }
    public Ticket createTicket(Ticket ticket) {
        return ticketDao.save(ticket);
    }

    public Ticket getTicketById(Integer ticketId) {

        return ticketDao.findById(ticketId).orElseThrow();
    }

    public Iterable<Ticket> getAllTickets() {
        return ticketDao.findAll();
    }

    public void deleteTicket(Integer ticketId) {
        ticketDao.deleteById(ticketId);
    }

    public Ticket updateTicket(int ticketId, String email) {
        Ticket ticket = ticketDao.findById(ticketId).orElseThrow();
        ticket.setEmail(email);
        return ticketDao.save(ticket);
    }
}
