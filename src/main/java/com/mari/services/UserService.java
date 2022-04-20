package com.mari.services;

import com.mari.domain.User;
import com.mari.repository.UserRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserService {

    private static final Logger logger=LogManager.getLogger();

    public User addUser(User user){
        logger.info("about to adduser: "+user);

        User userFromDB = new UserRepository().save(user);
        userFromDB.setHashedPassword(null);
        return userFromDB;
    }
}
