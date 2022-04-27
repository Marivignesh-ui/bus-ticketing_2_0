package com.mari.bus_ticketing2.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.mari.bus_ticketing2.domain.BlockedTicket;
import com.mari.bus_ticketing2.domain.BusRoute;
import com.mari.bus_ticketing2.domain.Ticket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public class TicketRepository {
    
    private static final Logger logger=LogManager.getLogger(TicketRepository.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public List<Ticket> getTickets(String busRouteId,String date){
        try {
            Session session=sessionFactory.getCurrentSession();
            logger.info("session created successfully to getTickets");
            Query<Ticket> query=session.createQuery("From Ticket where bus_route_id='"+busRouteId+"' and date='"+date+"'",Ticket.class);
            List<Ticket> tickets=query.getResultList();
            Query<BlockedTicket> query1=session.createQuery("From BlockedTicket where bus_route_id='"+busRouteId+"' and date='"+date+"'",BlockedTicket.class);
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
            Session session=sessionFactory.getCurrentSession();
            UUID busRouteId=UUID.fromString(seatId.substring(0,36));
            String date=seatId.substring(37,47);
            int seatNumber=Integer.parseInt(seatId.substring(48,50));
            BusRoute busRoute=session.get(BusRoute.class, busRouteId);
            BlockedTicket bTicket=new BlockedTicket(busRoute, date , true, seatId, seatNumber, new Date());
            session.save(bTicket);
            Query<BlockedTicket> query=session.createQuery("From BlockedTicket where seatId='"+seatId+"'",BlockedTicket.class);
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
            Session session=sessionFactory.getCurrentSession();
            logger.info("$$$$$$$$$************  ticket-list  ***********$$$$$$$$$: "+tickets);
            for(Ticket ticket:tickets){
                logger.info("$$$$$$$$$************ ticket ***********$$$$$$$$$: "+ticket);
                logger.info("$$$$$$$$$************blocked ticket***********$$$$$$$$$: "+ticket.getId());
                BlockedTicket bTicket=session.get(BlockedTicket.class, ticket.getId());
                session.save(ticket);
                logger.info("$$$$$$$$$************blocked ticket***********$$$$$$$$$: "+bTicket);
                session.delete(bTicket);
            }
            BusRoute busRoute=session.get(BusRoute.class, tickets.get(0).getBusRoute().getId());
            int remainingTickets=busRoute.getRemainingTickets()-tickets.size();
            busRoute.setRemainingTickets(remainingTickets);
            session.save(busRoute);
            return tickets;
        }catch(Exception e){
            logger.error("Error booking tickets");
            logger.catching(e);
            throw new RuntimeException("Error booking tickets");
        }
    }
    
}
