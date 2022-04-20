package com.mari.repository;

import javax.persistence.Query;

import com.mari.domain.User;
import com.mari.util.SessionUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

public class UserRepository {
    
    private static final Logger logger=LogManager.getLogger();
    
    public User save(User user) {
        logger.debug("about to insert into DB: "+user);

        try{
            Session session=SessionUtil.getSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();

            User userFromDB=null;
            try(Session session2=SessionUtil.getSession()){
                session2.beginTransaction();
                Query query=session2.createQuery("From User where email='"+user.getEmail()+"'");
                userFromDB=(User)query.getSingleResult();
            }
            return userFromDB;
        }catch(Exception e){
            logger.error("Error saving user");
            logger.catching(e);
            return null;
        }
    }

    public User findByMail(String email){
        logger.debug("about to fetch user from DB: "+email);
        User user=null;
        try(
            Session session=SessionUtil.getSession();
        ){
            session.beginTransaction();
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
