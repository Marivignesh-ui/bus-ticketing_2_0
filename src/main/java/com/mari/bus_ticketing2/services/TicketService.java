package com.mari.bus_ticketing2.services;

import java.util.List;

import com.mari.bus_ticketing2.domain.BlockedTicket;
import com.mari.bus_ticketing2.domain.Ticket;
import com.mari.bus_ticketing2.repository.TicketRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class TicketService {
    
    private static final Logger logger=LogManager.getLogger(TicketService.class);

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> getTicketService(String busRouteId,String date) {
        logger.info("called getTicketService with busRouteId: "+busRouteId);
        return ticketRepository.getTickets(busRouteId,date);
    }

    public BlockedTicket blockTicketService(String seatId){
        logger.info("called blockTicketService with seatId: "+seatId);
        return ticketRepository.blockTickets(seatId);
    }

    public List<Ticket> bookTicketService(List<Ticket> tickets){
        logger.info("called bookticketservice with tickets: "+tickets);
        return ticketRepository.bookTickets(tickets);
    }
}
