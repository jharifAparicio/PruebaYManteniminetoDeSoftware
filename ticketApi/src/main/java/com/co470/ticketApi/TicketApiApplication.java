package com.co470.ticketApi;

import com.co470.ticketApi.entities.Ticket;
import com.co470.ticketApi.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@SpringBootApplication
public class TicketApiApplication {

	public static void main(String[] args) {
		//SpringApplication.run(TicketApiApplication.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(TicketApiApplication.class, args);
		TicketService ticketservice = context.getBean("ticketService", TicketService.class);
		Ticket ticket = new Ticket();
		ticket.setTicketId(1);
		ticket.setBookingDate(new Date());
		ticket.setEmail("juan.perez@gmail.com");
		ticket.setPassengerName("Juan Perez");
		ticket.setSourceStation("Sucre");
		ticket.setDestStation("Potosi");
		ticketservice.createTicket(ticket);

	}
}

