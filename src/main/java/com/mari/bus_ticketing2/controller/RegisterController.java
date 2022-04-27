package com.mari.bus_ticketing2.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mari.bus_ticketing2.domain.User;
import com.mari.bus_ticketing2.domain.WebResponse;
import com.mari.bus_ticketing2.services.UserService;
import com.mari.bus_ticketing2.util.HashUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @PostMapping
    protected void doPost(@ModelAttribute("user")User user, HttpServletResponse resp) throws ServletException, IOException {
        String resString = null;
        try {
            // User user = new Gson().fromJson(req.getReader(), User.class);
            logger.info("got request from client to register" + user.getId());
            String hashedPassword = HashUtil.hash(user.getPassword().toCharArray());
            user.setHashedPassword(hashedPassword);
            user.setPassword(null);
            logger.info("about to call register service: " + user);
            User userFromDB = userService.addUser(user);

            WebResponse<User> webResponse = new WebResponse<User>(true, "User Registration Success", userFromDB);
            logger.info("returning response to client after registering user: " + webResponse);
            resString = new Gson().toJson(webResponse);
        } catch (Exception e) {
            logger.catching(e);
            logger.error("Error registering the user..");

            WebResponse<Object> webResponse = new WebResponse<>(false, "Error registering the user");
            logger.info("returning error response to client for register user: " + webResponse);
            resString = new Gson().toJson(webResponse);
            resp.setStatus(500);
        }
        resp.setContentType("application/json");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Header", "Content-Type");
        resp.getWriter().write(resString);
    }

    // @Override
    // protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    //     res.setHeader("Access-Control-Allow-Origin", "*");
    //     res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    //     res.setHeader("Access-Control-Allow-Header", "Content-Type");
    // }

}
