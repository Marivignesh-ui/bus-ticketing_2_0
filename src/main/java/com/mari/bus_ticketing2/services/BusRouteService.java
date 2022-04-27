package com.mari.bus_ticketing2.services;

import com.mari.bus_ticketing2.repository.BusRouteRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class BusRouteService {
    
    private static final Logger logger=LogManager.getLogger();

    @Autowired
    private BusRouteRepository busRouteRepository;

    public void createRoutesService(){
        logger.info("called create bus route service");
        busRouteRepository.createRoutes();
    }
}
