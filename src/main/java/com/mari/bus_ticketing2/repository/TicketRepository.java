package com.mari.bus_ticketing2.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.mari.bus_ticketing2.domain.BlockedTicket;
import com.mari.bus_ticketing2.domain.BusRoute;
import com.mari.bus_ticketing2.domain.Ticket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public class TicketRepository {
    
    private static final Logger logger=LogManager.getLogger(TicketRepository.class);

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public List<Ticket> getTickets(String busRouteId,String date){
        try {
            logger.info("session created successfully to getTickets");
            TypedQuery<Ticket> query=entityManager.createQuery("From Ticket where bus_route_id='"+busRouteId+"' and date='"+date+"'",Ticket.class);
            List<Ticket> tickets=query.getResultList();
            TypedQuery<BlockedTicket> query1=entityManager.createQuery("From BlockedTicket where bus_route_id='"+busRouteId+"' and date='"+date+"'",BlockedTicket.class);
            List<BlockedTicket> blockedTickets=query1.getResultList();
            for(BlockedTicket bTicket:blockedTickets){
                Ticket ticket=new Ticket(bTicket.getId(),bTicket.getBusRoute(),bTicket.getDate(),bTicket.getSeatNumber(),bTicket.getBookedAt());
                tickets.add(ticket);
            }
            return tickets;
        }catch(Exception e){
            logger.error("Error getting tickets");
            logger.catching(e);
            throw new RuntimeException("Error getting tickets");
        }
    }

    @Transactional
    public BlockedTicket blockTickets(String seatId) {    
        try {
            UUID busRouteId=UUID.fromString(seatId.substring(0,36));
            String date=seatId.substring(37,47);
            int seatNumber=Integer.parseInt(seatId.substring(48,50));
            BusRoute busRoute=entityManager.find(BusRoute.class, busRouteId);
            BlockedTicket bTicket=new BlockedTicket(busRoute, date , true, seatId, seatNumber, new Date());
            entityManager.persist(bTicket);
            TypedQuery<BlockedTicket> query=entityManager.createQuery("From BlockedTicket where seatId='"+seatId+"'",BlockedTicket.class);
            bTicket=query.getSingleResult();
            return bTicket;
        }catch(Exception e){
            logger.error("Error blocking tickets");
            logger.catching(e);
            throw new RuntimeException("Error blocking tickets");
        }
    }

    @Transactional
    public List<Ticket> bookTickets(List<Ticket> tickets){
        try {
            logger.info("$$$$$$$$$************  ticket-list  ***********$$$$$$$$$: "+tickets);
            for(Ticket ticket:tickets){
                logger.info("$$$$$$$$$************ ticket ***********$$$$$$$$$: "+ticket);
                logger.info("$$$$$$$$$************blocked ticket***********$$$$$$$$$: "+ticket.getId());
                BlockedTicket bTicket=entityManager.find(BlockedTicket.class, ticket.getId());
                entityManager.persist(ticket);
                logger.info("$$$$$$$$$************blocked ticket***********$$$$$$$$$: "+bTicket);
                entityManager.remove(bTicket);
            }
            BusRoute busRoute=entityManager.find(BusRoute.class, tickets.get(0).getBusRoute().getId());
            int remainingTickets=busRoute.getRemainingTickets()-tickets.size();
            busRoute.setRemainingTickets(remainingTickets);
            entityManager.persist(busRoute);
            return tickets;
        }catch(Exception e){
            logger.error("Error booking tickets");
            logger.catching(e);
            throw new RuntimeException("Error booking tickets");
        }
    }
    
}
