package com.hexaware.fastx_busticketsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.fastx_busticketsystem.entities.Ticket;
import com.hexaware.fastx_busticketsystem.exception.TicketNotFoundException;
import com.hexaware.fastx_busticketsystem.repository.BookingRepo;
import com.hexaware.fastx_busticketsystem.repository.TicketRepo;

import lombok.extern.slf4j.Slf4j;


/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:14-Aug-2025
Description:  TicketService Class test case*/


@SpringBootTest
@Slf4j
public class TicketServiceImplTest {

    @Autowired
    private ITicketService ticketService;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private TicketRepo ticketRepo;

    @Test
    void testGenerateTicketFromBooking() {
        
        int bookingId = 8;

        Ticket ticket = ticketService.generateTicketFromBooking(bookingId);
        log.info("Generated Ticket: ", ticket);

        assertNotNull(ticket);
        assertEquals(bookingId, ticket.getBooking().getBookingId());
        assertNotNull(ticket.getBus());
        assertNotNull(ticket.getTrip());
        assertEquals(LocalDate.now(), ticket.getIssueDate());
    }

    @Test
    void testGetTicketById() throws TicketNotFoundException {
       
        int ticketId = 1;

        Ticket ticket = ticketService.getTicketById(ticketId);
        log.info("Fetched Ticket by ID ", ticketId, ticket);

        assertNotNull(ticket);
        assertEquals(ticketId, ticket.getTicketId());
    }

    @Test
    void testGetAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        log.info("Total Tickets Found: ", tickets.size());

        assertNotNull(tickets);
        assertTrue(tickets.size() > 0);
    }
}