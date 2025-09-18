package com.co470.ticketApi.controller;

import com.co470.ticketApi.entities.Ticket;
import com.co470.ticketApi.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="api/tickets")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @PostMapping(value="/create")
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketService.createTicket(ticket);
    }

    @GetMapping(value="/ticket/{ticketId}")
    public Ticket getTicketById(@PathVariable("ticketId")Integer ticketId) {
        //return ticketService.getTicketById(ticketId).orElseThrow();
        return ticketService.getTicketById(ticketId);
    }

    @GetMapping(value="/ticket/alltickets")
    public Iterable<Ticket> getAllTickets(){
        return ticketService.getAllTickets();
    }

    @DeleteMapping(value="/ticket/{ticketId}")
    public void deleteTicket(@PathVariable("ticketId")Integer ticketId) {
        ticketService.deleteTicket(ticketId);
    }

    @PutMapping(value="/ticket/{ticketId}/{newEmail:.+}")
    public Ticket updateTicket(@PathVariable("ticketId")int  ticketId, @PathVariable("newEmail")String email) {
        return ticketService.updateTicket(ticketId, email);
    }
}
