package com.mari.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import com.mari.domain.Bus;
import com.mari.domain.BusRoute;
import com.mari.util.SessionUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class BusRepository {

    private static final Logger logger=LogManager.getLogger();

    public List<Bus> getBuses(String date,String startTerminal,String endTerminal,boolean flag){
        try(Session session=SessionUtil.getSession()){
            session.beginTransaction();
            Query<Bus> query=session.createQuery("From Bus where startTerminal='"+startTerminal+"' and endTerminal='"+endTerminal+"'",Bus.class);
            List<Bus> buses=query.getResultList();
            for(int i=0;i<buses.size();i++){
                Query<BusRoute> query1=session.createQuery("From BusRoute where bus_id='"+buses.get(i).getId()+"' and date_of_journey='"+date+"'",BusRoute.class);
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
    
    public Bus createBus(Bus bus) {
        try(Session session=SessionUtil.getSession()){
            session.beginTransaction();
            session.save(bus);
            session.getTransaction().commit();
            Bus busFromDB=null;
            try(Session session2=SessionUtil.getSession()){
                session2.beginTransaction();
                Query<Bus> query=session2.createQuery("From Bus where registrationNumber='"+bus.getRegistrationNumber()+"'",Bus.class);
                busFromDB=(Bus)query.getSingleResult();
            }
            logger.info("returning response: "+bus);
            return busFromDB;
        }catch(Exception e){
            logger.error("Error creating bus");
            logger.catching(e);
            throw new RuntimeException("Error creating bus");
        }
    }

    public Bus updateBus(UUID busId,String startTerminal,String endTerminal,String journeyType){
        try(Session session=SessionUtil.getSession()){
            session.beginTransaction();
            Bus bus=session.get(Bus.class, busId);
            bus.setStartTerminal(startTerminal);
            bus.setEndTerminal(endTerminal);
            bus.setJourneyType(journeyType);
            session.save(bus);
            session.getTransaction().commit();

            try(Session session2=SessionUtil.getSession()){
                session2.beginTransaction();
                bus=session2.get(Bus.class, busId);
            }
            return bus;
        }catch(Exception e){
            logger.error("Error updating bus");
            logger.catching(e);
            throw new RuntimeException("Error updating bus");
        }
    }

    public void deleteBus(UUID busId){
        try(Session session=SessionUtil.getSession()){
            session.beginTransaction();
            Bus bus=session.get(Bus.class, busId);
            session.delete(bus);
            session.getTransaction().commit();

        }catch(Exception e){
            logger.error("Error updating bus");
            logger.catching(e);
            throw new RuntimeException("Error updating bus");
        }
    }
    
}
