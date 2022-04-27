package com.mari.bus_ticketing2.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.mari.bus_ticketing2.domain.BlockedTicket;
import com.mari.bus_ticketing2.domain.Ticket;
import com.mari.bus_ticketing2.domain.TicketDto;
import com.mari.bus_ticketing2.domain.WebResponse;
import com.mari.bus_ticketing2.services.TicketService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketController{
    
    private static final Logger logger=LogManager.getLogger(TicketController.class);

    private final TicketService ticketService;
    
    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    protected WebResponse<List<Ticket>> doGet(@RequestParam String busRouteId,@RequestParam String date, HttpServletResponse resp) {
        WebResponse<List<Ticket>> webResponse;
        try {
            //String busId=req.getParameter("bus_id");
            List<Ticket> tickets=ticketService.getTicketService(busRouteId,date);
            for(int i=0;i<tickets.size();i++){
               tickets.get(i).getBusRoute().setBus(null);
            }
            webResponse=new WebResponse<List<Ticket>>(true, "Tickets retrived successfully", tickets);
            resp.setStatus(200);
        }catch(Exception e){
            logger.error("Error getting Bus");
            logger.catching(e);
            resp.setStatus(500);
            webResponse=new WebResponse<>(false, "Error Getting Tickets");
        }
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Header", "Content-Type");
        resp.setContentType("application/json");
        return webResponse;
    }

    @PostMapping
    protected WebResponse<BlockedTicket> doPost(@RequestBody TicketDto ticketDto, HttpServletResponse resp) {
        WebResponse<BlockedTicket> webResponse;
        try{
            BlockedTicket bTicket=ticketService.blockTicketService(ticketDto.getSeatId());
            bTicket.setBusRoute(null);
            webResponse=new WebResponse<>(true, "blocked tickets Successfully",bTicket);
        }catch(Exception e){
            logger.error("Error blocking tickets");
            logger.catching(e);
            resp.setStatus(500);
            webResponse=new WebResponse<>(false, "Error blocking tickets");
        }
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Header", "Content-Type");
        resp.setContentType("application/json");
        return webResponse;
    }

    @PutMapping
    protected WebResponse<List<Ticket>> doPut(@RequestBody List<Ticket> tickets, HttpServletResponse resp) {
        WebResponse<List<Ticket>> webResponse=null;
        try {
            logger.debug("Request to book tickets received successfully!!");
            List<Ticket> ticketsfromDB=null;
            logger.info("********-------------Request Object read Successfully-----------*******:"+tickets);
            logger.info("!! About to call bookTicketService");
            ticketsfromDB=ticketService.bookTicketService(tickets);
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            resp.setHeader("Access-Control-Allow-Header", "Content-Type");
            resp.setStatus(200);
            resp.setContentType("application/json");
            webResponse=new WebResponse<List<Ticket>>(true,"ticket booked successfully",ticketsfromDB);
        }catch(Exception e){
            logger.error(e);
            logger.catching(e);
            resp.setStatus(500);
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            resp.setHeader("Access-Control-Allow-Header", "Content-Type");
            webResponse=new WebResponse<List<Ticket>>(true,"ticket booking failed");
        }   
        return webResponse;
    }


    // protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    //     res.setHeader("Access-Control-Allow-Origin", "*");
    //     res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");   
    //     res.setHeader("Access-Control-Allow-Header", "Content-Type");
    // }
}
