package com.mari.bus_ticketing2.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// @WebServlet(urlPatterns = {"/bus"})
@Controller
@RequestMapping("/bus")
public class BusController {

    private static final Logger logger=LogManager.getLogger(BusController.class);

    @Autowired
    private BusService busService;

    @GetMapping
    protected String doGet(@RequestParam("startterminal")String startTerminal,
                        @RequestParam("endterminal")String endTerminal,
                        @RequestParam("date")String date,  
                        HttpServletResponse resp,
                        Model model
    ){
        WebResponse<List<Bus>> webResponse;
        String resString=null;
        try {
            //String startTerminal=req.getParameter("startterminal");
            // String endTerminal=req.getParameter("endterminal");
            // String date=req.getParameter("date");

            logger.info("request recieved startterminal:"+startTerminal+" endTerminal "+endTerminal+" date: "+date);

            List<Bus> buses=busService.getBusesService(date,startTerminal,endTerminal);
            
            // webResponse=new WebResponse<List<Bus>>(true, "Bus List Retrived sucessfully", buses);
            resp.setStatus(200);
            model.addAttribute("bus", buses);
            resString="HomePage";
        }catch(Exception e){
            logger.error("Error getting Bus");
            logger.catching(e);
            resp.setStatus(500);
            webResponse=new WebResponse<>(false, "Error Getting Bus");
        }
        return resString;
        // Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        // resString=gson.toJson(webResponse);
        // resp.setHeader("Access-Control-Allow-Origin", "*");
        // resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        // resp.setHeader("Access-Control-Allow-Header", "Content-Type");
        // resp.setContentType("application/json");
        // try {
        //     resp.getWriter().write(resString);
        // } catch (IOException e) {
        //     logger.catching(e);
        // }
    }

    @PostMapping
    public void doPost(HttpServletRequest req,HttpServletResponse res) {
        String resString=null;
        WebResponse<Bus> webResponse=null;
        try(JsonReader reader=new JsonReader(req.getReader())){
            Bus bus=new Gson().fromJson(reader, Bus.class);
            logger.info("request object read successfully!! : "+bus);

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
        try {
            res.getWriter().write(resString);
        } catch (IOException e) {
            logger.catching(e);            
        }
    }

    @PutMapping
    public void doPut(HttpServletRequest req,HttpServletResponse res) {
        String resString=null;
        WebResponse<Bus> webResponse=null;
        try(JsonReader reader=new JsonReader(req.getReader())){
            reader.beginObject();
            UUID busId=(reader.nextName().equals("busId"))?UUID.fromString(reader.nextString()):null;
            String startTerminal=(reader.nextName().equals("startTerminal"))?reader.nextString():null;;
            String endTerminal=(reader.nextName().equals("endTerminal"))?reader.nextString():null;;
            String journeyType=(reader.nextName().equals("journeyType"))?reader.nextString():null;;
            reader.endObject();

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
        try {
            res.getWriter().write(resString);
        } catch (IOException e) {
            logger.catching(e);
        }
    }

    @DeleteMapping
    public void doDelete(HttpServletRequest req,HttpServletResponse res) {
        String resString=null;
        WebResponse<Bus> webResponse=null;
        try(JsonReader reader=new JsonReader(req.getReader())){
            reader.beginObject();
            UUID busId=(reader.nextName().equals("busId"))?UUID.fromString(reader.nextString()):null;
            reader.endObject();

            logger.info("request object read successfully!! "+busId);

            busService.deleteBusService(busId);   

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
        try {
            res.getWriter().write(resString);
        } catch (IOException e) {
            logger.catching(e);
        }
    }

    
    protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");   
        res.setHeader("Access-Control-Allow-Header", "Content-Type");
    }
    
}
