package com.mari.bus_ticketing2.services;

import com.mari.bus_ticketing2.domain.User;
import com.mari.bus_ticketing2.repository.UserRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserService {

    private static final Logger logger=LogManager.getLogger();

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user){
        logger.info("about to adduser: "+user);

        User userFromDB = userRepository.save(user);
        userFromDB.setHashedPassword(null);
        return userFromDB;
    }
}
