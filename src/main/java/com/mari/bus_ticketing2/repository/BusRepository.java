package com.mari.bus_ticketing2.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.mari.bus_ticketing2.domain.Bus;
import com.mari.bus_ticketing2.domain.BusRoute;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public class BusRepository {

    private static final Logger logger=LogManager.getLogger();

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public List<Bus> getBuses(String date,String startTerminal,String endTerminal,boolean flag){
        try {
            TypedQuery<Bus> query=entityManager.createQuery("From Bus where startTerminal='"+startTerminal+"' and endTerminal='"+endTerminal+"'",Bus.class);
            List<Bus> buses=query.getResultList();
            for(int i=0;i<buses.size();i++){
                TypedQuery<BusRoute> query1=entityManager.createQuery("From BusRoute where bus_id='"+buses.get(i).getId()+"' and date_of_journey='"+date+"'",BusRoute.class);
                buses.get(i).setBusRoutes(new ArrayList<>());
                List<BusRoute> busRoutes=query1.getResultList();
                if(busRoutes.isEmpty()){
                    buses.remove(i);
                }else{
                    buses.get(i).getBusRoutes().addAll(busRoutes);
                }  
            }
            if(buses.isEmpty() && flag){
                new BusRouteRepository().createRoutes();
                getBuses(date, startTerminal, endTerminal,false);
            }
            logger.info("returning response /////###????*****//// "+buses);
            return buses;
        }catch(Exception e){
            logger.error("Error Getting buses");
            logger.catching(e);
            throw new RuntimeException("Error Getting buses");
        }
    }
    
    @Transactional
    public Bus createBus(Bus bus) {
        try {
            entityManager.persist(bus);
            Bus busFromDB=getBusByRegistrationNumber(bus.getRegistrationNumber());
            logger.info("returning response: "+bus);
            return busFromDB;
        }catch(Exception e){
            logger.error("Error creating bus");
            logger.catching(e);
            throw new RuntimeException("Error creating bus");
        }
    }

    @Transactional
    public Bus getBusByRegistrationNumber(String registrationNumber){
        try {
            TypedQuery<Bus> query=entityManager.createQuery("From Bus where registrationNumber='"+registrationNumber+"'",Bus.class);
            return (Bus)query.getSingleResult();
        }catch(Exception e){
            logger.error("Error Getting bus by RegistrationNumber: "+registrationNumber);
            logger.catching(e);
            throw new RuntimeException("Error Getting bus by RegistrationNumber: "+registrationNumber);
        }
    }

    @Transactional
    public Bus updateBus(UUID busId,String startTerminal,String endTerminal,String journeyType){
        try {
            Bus bus=entityManager.find(Bus.class, busId);
            bus.setStartTerminal(startTerminal);
            bus.setEndTerminal(endTerminal);
            bus.setJourneyType(journeyType);
            entityManager.persist(bus);

            bus=getBusById(busId);
        
            return bus;
        }catch(Exception e){
            logger.error("Error updating bus");
            logger.catching(e);
            throw new RuntimeException("Error updating bus");
        }
    }

    @Transactional
    public Bus getBusById(UUID busId){
        try {
            return entityManager.find(Bus.class, busId);
        }catch(Exception e){
            logger.error("Error Getting bus by Id: "+busId);
            logger.catching(e);
            throw new RuntimeException("Error Getting bus by Id: "+busId);
        }
    }

    @Transactional
    public void deleteBus(UUID busId){
        try {
            Bus bus=entityManager.find(Bus.class, busId);
            entityManager.remove(bus);
        }catch(Exception e){
            logger.error("Error updating bus");
            logger.catching(e);
            throw new RuntimeException("Error updating bus");
        }
    }
    
}
