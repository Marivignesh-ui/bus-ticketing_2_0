package com.mari.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.mari.domain.BlockedTicket;
import com.mari.domain.BusRoute;
import com.mari.domain.Ticket;
import com.mari.util.SessionUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class TicketRepository {
    
    private static final Logger logger=LogManager.getLogger(TicketRepository.class);

    public List<Ticket> getTickets(String busRouteId,String date){
        try(Session session=SessionUtil.getSession()){
            logger.info("session created successfully to getTickets");
            session.beginTransaction();
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

    public BlockedTicket blockTickets(String seatId) {    
        try(Session session=SessionUtil.getSession()){
            session.beginTransaction();
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

    public List<Ticket> bookTickets(List<Ticket> tickets){
        try(Session session=SessionUtil.getSession()){
            session.beginTransaction();
            for(Ticket ticket:tickets){
                session.save(ticket);
                BlockedTicket bTicket=session.get(BlockedTicket.class, ticket.getId());
                session.delete(bTicket);
            }
            session.getTransaction().commit();
            return tickets;
        }catch(Exception e){
            logger.error("Error booking tickets");
            logger.catching(e);
            throw new RuntimeException("Error booking tickets");
        }
    }
    
}
