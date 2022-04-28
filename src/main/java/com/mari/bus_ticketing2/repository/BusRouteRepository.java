package com.mari.bus_ticketing2.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.mari.bus_ticketing2.domain.Bus;
import com.mari.bus_ticketing2.domain.BusRoute;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class BusRouteRepository {
    
    private static final Logger logger=LogManager.getLogger(BusRouteRepository.class);

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void createRoutes(){
        try {
            logger.info("Session initiated to create bus routes");
            TypedQuery<Bus> query=entityManager.createQuery("From Bus",Bus.class);
            List<Bus> buses=query.getResultList();
            for(Bus bus:buses){
                TypedQuery<String> query1=entityManager.createQuery("select MAX(date) From BusRoute where bus_id='"+bus.getId()+"'",String.class);
                String dateString=(String)query1.getSingleResult();
                Date utilDate=new SimpleDateFormat("yyyy-mm-dd").parse(dateString);
                if(bus.getJourneyType().toLowerCase().equals("everyday")){
                    for(int i=1;i<=7;i++){
                        DateTime dateTime=new DateTime(utilDate);
                        utilDate=dateTime.plusDays(1).toDate();
                        dateString=new SimpleDateFormat("yyyy-mm-dd").format(utilDate);
                    }
                }else if(bus.getJourneyType().toLowerCase().equals("alternative")){
                    for(int i=1;i<=7;i++){
                        DateTime dateTime=new DateTime(utilDate);
                        utilDate=dateTime.plusDays(2).toDate();
                        dateString=new SimpleDateFormat("yyyy-mm-dd").format(utilDate);
                    }
                }
                BusRoute busRoute=new BusRoute(bus, dateString, bus.getTotalSeats());
                entityManager.persist(busRoute);
            }
        } catch (ParseException e) {
            logger.error("Error creating Bus routes");
            logger.catching(e);
            throw new RuntimeException("Error creating Bus Routes");
        }
    }
}



