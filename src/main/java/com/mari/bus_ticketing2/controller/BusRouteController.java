package com.mari.bus_ticketing2.controller;


import com.mari.bus_ticketing2.services.BusRouteService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/createRoutes")
public class BusRouteController {

    private static final Logger logger=LogManager.getLogger();

    @Autowired
    private BusRouteService busRouteService;

    @GetMapping
    public void doGet(){
        try{
            logger.info("Request received to create routes");
            busRouteService.createRoutesService();
        }catch(Exception e){
            logger.error("Error creating routes");
            logger.catching(e);
        }
    }
}
