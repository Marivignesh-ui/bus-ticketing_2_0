package com.mari.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.mari.domain.BlockedTicket;
import com.mari.domain.Ticket;
import com.mari.domain.WebResponse;
import com.mari.services.TicketService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/tickets")
public class TicketController extends HttpServlet{
    
    private static final Logger logger=LogManager.getLogger(TicketController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebResponse<List<Ticket>> webResponse;
        String resString=null;
        try {
            //String busId=req.getParameter("bus_id");
            String busRouteId=req.getParameter("route_id");
            String date=req.getParameter("date");

            List<Ticket> tickets=new TicketService().getTicketService(busRouteId,date);
            webResponse=new WebResponse<List<Ticket>>(true, "Tickets retrived successfully", tickets);
            resp.setStatus(200);
        }catch(Exception e){
            logger.error("Error getting Bus");
            logger.catching(e);
            resp.setStatus(500);
            webResponse=new WebResponse<>(false, "Error Getting Tickets");
        }
        Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        resString=gson.toJson(webResponse);
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Header", "Content-Type");
        resp.setContentType("application/json");
        resp.getWriter().write(resString);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebResponse<BlockedTicket> webResponse;
        String resString=null;
        try(JsonReader reader=new JsonReader(req.getReader())){
            reader.beginObject();
            String seatId=(reader.nextName().equals("seatId"))?reader.nextString():null;
            reader.endObject();

            BlockedTicket bTicket=new TicketService().blockTicketService(seatId);
            webResponse=new WebResponse<>(true, "blocked tickets Successfully",bTicket);
        }catch(Exception e){
            logger.error("Error blocking tickets");
            logger.catching(e);
            resp.setStatus(500);
            webResponse=new WebResponse<>(false, "Error blocking tickets");
        }
        Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        resString=gson.toJson(webResponse);
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Header", "Content-Type");
        resp.setContentType("application/json");
        resp.getWriter().write(resString);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try(JsonReader reader=new JsonReader(req.getReader())){
            logger.debug("Request to book tickets received successfully!!");
            reader.beginArray();
            List<Ticket> tickets=new ArrayList<Ticket>();
            while(reader.hasNext()){
                tickets.add(new Gson().fromJson(reader,Ticket.class));
            }
            reader.endArray();
            List<Ticket> ticketsfromDB=null;
            logger.info("********-------------Request Object read Successfully-----------*******:"+tickets);
            logger.info("!! About to call bookTicketService");
            ticketsfromDB=new TicketService().bookTicketService(tickets);
            try(PrintWriter writer=resp.getWriter()){
                resp.setHeader("Access-Control-Allow-Origin", "*");
                resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                resp.setHeader("Access-Control-Allow-Header", "Content-Type");
                resp.setStatus(200);
                resp.setContentType("application/json");
                writer.println(new Gson().toJson(ticketsfromDB));
                writer.flush();
            }
        }catch(Exception e){
            logger.error(e);
            logger.catching(e);
            resp.setStatus(500);
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            resp.setHeader("Access-Control-Allow-Header", "Content-Type");
        }   
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");   
        res.setHeader("Access-Control-Allow-Header", "Content-Type");
    }
}
