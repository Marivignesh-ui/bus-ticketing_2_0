package com.mari.services;

import java.util.List;
import java.util.UUID;


import com.mari.domain.Bus;
import com.mari.repository.BusRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BusService {
    
    private static final Logger logger=LogManager.getLogger(BusService.class);

    public List<Bus> getBusesService(String date,String startTerminal,String endTerminal) {
        logger.info("called getBusservice: date: "+date+" startTerminal "+startTerminal+" endTerminal "+endTerminal);
        BusRepository busRepository=new BusRepository();
        return busRepository.getBuses(date,startTerminal,endTerminal,true);
    }

    public Bus createBusService(Bus bus){
        logger.info("called createBusService: "+bus.getId());
        BusRepository busRepository=new BusRepository();
        return busRepository.createBus(bus);
    }

    public Bus updateBusService(UUID busId,String startTerminal,String endTerminal,String journeyType){
        logger.info("called updateBusService: "+busId);
        BusRepository busRepository=new BusRepository();
        return busRepository.updateBus(busId,startTerminal,endTerminal,journeyType);
    }

    public void deleteBusService(UUID busId){
        logger.info("called createBusService: "+busId);
        BusRepository busRepository=new BusRepository();
        busRepository.deleteBus(busId);
    }
}
