package com.mari.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mari.domain.User;
import com.mari.domain.WebResponse;
import com.mari.services.UserService;
import com.mari.util.HashUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = { "/register" })
public class RegisterController extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String resString = null;
        try {
            User user = new Gson().fromJson(req.getReader(), User.class);
            logger.info("got request from client to register" + user.getId());
            String hashedPassword = HashUtil.hash(user.getPassword().toCharArray());
            user.setHashedPassword(hashedPassword);
            user.setPassword(null);
            logger.info("about to call register service: " + user);
            User userFromDB = new UserService().addUser(user);

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

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        res.setHeader("Access-Control-Allow-Header", "Content-Type");
    }

}
