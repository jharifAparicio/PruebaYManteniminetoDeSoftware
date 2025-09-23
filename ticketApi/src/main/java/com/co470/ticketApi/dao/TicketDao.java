package com.co470.ticketApi.dao;

import com.co470.ticketApi.entities.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketDao extends CrudRepository<Ticket, Integer> {
    Ticket findByEmail(String email);

}
