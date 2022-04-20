package com.mari.services;

import com.mari.repository.BusRouteRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BusRouteService {
    
    private static final Logger logger=LogManager.getLogger();

    public void createRoutesService(){
        logger.info("called create bus route service");
        BusRouteRepository busRouteRepository=new BusRouteRepository();
        busRouteRepository.createRoutes();
    }
}
