package com.mari.bus_ticketing2.services;

import com.mari.bus_ticketing2.domain.User;
import com.mari.bus_ticketing2.repository.UserRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginService {
    
    private static final Logger logger=LogManager.getLogger(LoginService.class);

    public User login(String email){
        logger.info("called login service "+email);
        return new UserRepository().findByMail(email);
    }
}
