package com.mari.bus_ticketing2.util;

import com.mari.bus_ticketing2.domain.BlockedTicket;
import com.mari.bus_ticketing2.domain.Bus;
import com.mari.bus_ticketing2.domain.BusRoute;
import com.mari.bus_ticketing2.domain.Order;
import com.mari.bus_ticketing2.domain.Ticket;
import com.mari.bus_ticketing2.domain.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionUtil {
    public static Session getSession(){
        SessionFactory sessionFactory=new Configuration().configure("hibernate.cfg.xml")
                                    .addAnnotatedClass(User.class)
                                    .addAnnotatedClass(Bus.class)
                                    .addAnnotatedClass(BusRoute.class)
                                    .addAnnotatedClass(Ticket.class)
                                    .addAnnotatedClass(BlockedTicket.class)
                                    .addAnnotatedClass(Order.class)
                                    .buildSessionFactory();
                        
        return sessionFactory.getCurrentSession();
    }
}
