package com.mari.bus_ticketing2.services;

import com.mari.bus_ticketing2.domain.User;
import com.mari.bus_ticketing2.repository.UserRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LoginService {
    
    private static final Logger logger=LogManager.getLogger(LoginService.class);

    @Autowired
    private UserRepository userRepository;

    public User login(String email){
        logger.info("called login service "+email);
        return userRepository.findByMail(email);
    }
}
