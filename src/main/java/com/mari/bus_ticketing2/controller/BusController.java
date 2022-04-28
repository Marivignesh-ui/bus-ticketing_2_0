package com.mari.bus_ticketing2.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mari.bus_ticketing2.domain.Bus;
import com.mari.bus_ticketing2.domain.WebResponse;
import com.mari.bus_ticketing2.services.BusService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bus")
public class BusController {

    private static final Logger logger=LogManager.getLogger(BusController.class);

    private final BusService busService;
    
    @Autowired
    public BusController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping
    protected WebResponse<List<Bus>> doGet(@RequestParam("startterminal")String startTerminal,
                        @RequestParam("endterminal")String endTerminal,
                        @RequestParam("date")String date,  
                        HttpServletResponse resp
    ){
        WebResponse<List<Bus>> webResponse=null;
        try {
            logger.info("request recieved startterminal:"+startTerminal+" endTerminal "+endTerminal+" date: "+date);

            List<Bus> buses=busService.getBusesService(date,startTerminal,endTerminal);
            for(int i=0;i<buses.size();i++){
                for(int j=0;j<buses.get(i).getBusRoutes().size();j++){
                    buses.get(i).getBusRoutes().get(j).setBus(null);
                }
            }
            webResponse=new WebResponse<List<Bus>>(true, "Bus List Retrived sucessfully", buses);
            resp.setStatus(200);
        }catch(Exception e){
            logger.error("Error getting Bus");
            logger.catching(e);
            resp.setStatus(500);
            webResponse=new WebResponse<>(false, "Error Getting Bus");
        }
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Header", "Content-Type");
        resp.setContentType("application/json");
        return webResponse;
    }

    @PostMapping
    public WebResponse<Bus> doPost(@RequestBody Bus bus,HttpServletResponse res) {
        WebResponse<Bus> webResponse=null;
        try{
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
        res.setContentType("application/json");
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        res.setHeader("Access-Control-Allow-Header", "Content-Type");
        return webResponse;
    }

    @PutMapping
    public WebResponse<Bus> doPut(@RequestBody Bus bus,HttpServletResponse res) {
        WebResponse<Bus> webResponse=null;
        try{ 
            logger.info("Request received to update:"+bus);
            Bus busfromDB=busService.updateBusService(bus.getId(),bus.getStartTerminal(),bus.getEndTerminal(),bus.getJourneyType());
            busfromDB.setBusRoutes(null);
            webResponse=new WebResponse<Bus>(true, "successfully updated bus details!!",busfromDB);
            res.setStatus(200);
        }catch(Exception e){
            logger.error(e);
            logger.catching(e);
            res.setStatus(500);
            webResponse=new WebResponse<>(false, "Error updating bus details");
        }
        res.setContentType("application/json");
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        res.setHeader("Access-Control-Allow-Header", "Content-Type");
        return webResponse;
    }

    @DeleteMapping("/{id}")
    public WebResponse<Bus> doDelete(@PathVariable String id,HttpServletResponse res) {
        WebResponse<Bus> webResponse=null;
        try {
            UUID busId=UUID.fromString(id);

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
        res.setContentType("application/json");
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        res.setHeader("Access-Control-Allow-Header", "Content-Type");
        return webResponse;
    }

    
    protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");   
        res.setHeader("Access-Control-Allow-Header", "Content-Type");
    }
    
}
