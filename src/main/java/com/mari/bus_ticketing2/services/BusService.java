package com.mari.bus_ticketing2.services;

import java.util.List;
import java.util.UUID;

import com.mari.bus_ticketing2.domain.Bus;
import com.mari.bus_ticketing2.repository.BusRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class BusService {
    
    private static final Logger logger=LogManager.getLogger(BusService.class);

    @Autowired
    private BusRepository busRepository;

    public List<Bus> getBusesService(String date,String startTerminal,String endTerminal) {
        logger.info("called getBusservice: date: "+date+" startTerminal "+startTerminal+" endTerminal "+endTerminal);
        return busRepository.getBuses(date,startTerminal,endTerminal,true);
    }

    public Bus createBusService(Bus bus){
        logger.info("called createBusService: "+bus.getId());
        return busRepository.createBus(bus);
    }

    public Bus updateBusService(UUID busId,String startTerminal,String endTerminal,String journeyType){
        logger.info("called updateBusService: "+busId);
        return busRepository.updateBus(busId,startTerminal,endTerminal,journeyType);
    }

    public void deleteBusService(UUID busId){
        logger.info("called createBusService: "+busId);
        busRepository.deleteBus(busId);
    }
}
