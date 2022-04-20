package com.mari.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mari.services.BusRouteService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = {"/createRoutes"})
public class BusRouteController extends HttpServlet{

    private static final Logger logger=LogManager.getLogger();

    @Override
    public void doGet(HttpServletRequest req,HttpServletResponse res){
        try{
            logger.info("Request received to create routes");
            BusRouteService busRouteService=new BusRouteService();
            busRouteService.createRoutesService();
        }catch(Exception e){
            logger.error("Error creating routes");
            logger.catching(e);
        }
    }
}
