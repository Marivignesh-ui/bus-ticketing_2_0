package com.mari.bus_ticketing2.repository;

import javax.persistence.Query;

import com.mari.bus_ticketing2.domain.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public class UserRepository {
    
    private static final Logger logger=LogManager.getLogger();

    @Autowired
    private SessionFactory sessionFactory;
    
    @Transactional
    public User save(User user) {
        logger.debug("about to insert into DB: "+user);

        try{
            Session session=sessionFactory.getCurrentSession();
            session.save(user);

            User userFromDB=findByMail(user.getEmail());
            return userFromDB;
        }catch(Exception e){
            logger.error("Error saving user");
            logger.catching(e);
            return null;
        }
    }

    @Transactional
    public User findByMail(String email){
        logger.debug("about to fetch user from DB: "+email);
        User user=null;
        try{
            Session session=sessionFactory.getCurrentSession();
            Query query=session.createQuery("From User where email='"+email+"'");
            user=(User)query.getSingleResult();
        }catch(Exception e){
            logger.error("Error getting by User mail"+email);
            logger.catching(e);
            throw new RuntimeException("Error getting user by mail: "+email);
        }
        return user;
    }
}
