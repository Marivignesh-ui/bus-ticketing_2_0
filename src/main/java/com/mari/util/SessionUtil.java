package com.mari.util;

import com.mari.domain.BlockedTicket;
import com.mari.domain.Bus;
import com.mari.domain.BusRoute;
import com.mari.domain.Order;
import com.mari.domain.Ticket;
import com.mari.domain.User;

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
