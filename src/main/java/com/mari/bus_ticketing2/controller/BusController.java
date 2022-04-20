package com.mari.bus_ticketing2.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.mari.bus_ticketing2.domain.Bus;
import com.mari.bus_ticketing2.domain.WebResponse;
import com.mari.bus_ticketing2.services.BusService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = {"/bus"})
public class BusController extends HttpServlet{

    private static final Logger logger=LogManager.getLogger(BusController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebResponse<List<Bus>> webResponse;
        String resString=null;
        try {
            String startTerminal=req.getParameter("startterminal");
            String endTerminal=req.getParameter("endterminal");
            String date=req.getParameter("date");

            logger.info("request recieved startterminal:"+startTerminal+" endTerminal "+endTerminal+" date: "+date);

            BusService busService=new BusService();
            List<Bus> buses=busService.getBusesService(date,startTerminal,endTerminal);
            
            webResponse=new WebResponse<List<Bus>>(true, "Bus List Retrived sucessfully", buses);
            resp.setStatus(200);
        }catch(Exception e){
            logger.error("Error getting Bus");
            logger.catching(e);
            resp.setStatus(500);
            webResponse=new WebResponse<>(false, "Error Getting Bus");
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
    public void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException{
        String resString=null;
        WebResponse<Bus> webResponse=null;
        try(JsonReader reader=new JsonReader(req.getReader())){
            Bus bus=new Gson().fromJson(reader, Bus.class);
            logger.info("request object read successfully!! : "+bus);

            BusService busService=new BusService();
            bus=busService.createBusService(bus);
            bus.setBusRoutes(null);

            webResponse=new WebResponse<Bus>(true, "successfully created bus", bus);
            res.setStatus(200);
        }catch(Exception e){
            logger.error("Error Creating Bus");
            logger.catching(e);
            res.setStatus(500);
            webResponse=new WebResponse<>(false, "Error creating Bus");
        }
        resString=new Gson().toJson(webResponse);
        res.setContentType("application/json");
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        res.setHeader("Access-Control-Allow-Header", "Content-Type");
        res.getWriter().write(resString);
    }

    @Override
    public void doPut(HttpServletRequest req,HttpServletResponse res) throws IOException{
        String resString=null;
        WebResponse<Bus> webResponse=null;
        try(JsonReader reader=new JsonReader(req.getReader())){
            reader.beginObject();
            UUID busId=(reader.nextName().equals("busId"))?UUID.fromString(reader.nextString()):null;
            String startTerminal=(reader.nextName().equals("startTerminal"))?reader.nextString():null;;
            String endTerminal=(reader.nextName().equals("endTerminal"))?reader.nextString():null;;
            String journeyType=(reader.nextName().equals("journeyType"))?reader.nextString():null;;
            reader.endObject();


            BusService busService=new BusService();
            Bus bus=busService.updateBusService(busId,startTerminal,endTerminal,journeyType);
            bus.setBusRoutes(null);
            webResponse=new WebResponse<Bus>(true, "successfully updated bus details!!",bus);
            res.setStatus(200);
        }catch(Exception e){
            logger.error(e);
            logger.catching(e);
            res.setStatus(500);
            webResponse=new WebResponse<>(false, "Error updating bus details");
        }
        resString=new Gson().toJson(webResponse);
        res.setContentType("application/json");
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        res.setHeader("Access-Control-Allow-Header", "Content-Type");
        res.getWriter().write(resString);
    }

    @Override
    public void doDelete(HttpServletRequest req,HttpServletResponse res) throws IOException{
        String resString=null;
        WebResponse<Bus> webResponse=null;
        try(JsonReader reader=new JsonReader(req.getReader())){
            reader.beginObject();
            UUID busId=(reader.nextName().equals("busId"))?UUID.fromString(reader.nextString()):null;
            reader.endObject();

            logger.info("request object read successfully!! "+busId);

            BusService busServices=new BusService();
            busServices.deleteBusService(busId);   

            webResponse=new WebResponse<Bus>(true, "deleted bus successfully!!");
            res.setStatus(200);
        }catch(Exception e){
            logger.error(e);
            logger.catching(e);
            res.setStatus(500);
            webResponse=new WebResponse<Bus>(false, "Error deleting bus");
        }
        resString=new Gson().toJson(webResponse);
        res.setContentType("application/json");
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        res.setHeader("Access-Control-Allow-Header", "Content-Type");
        res.getWriter().write(resString);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");   
        res.setHeader("Access-Control-Allow-Header", "Content-Type");
    }
    
}
