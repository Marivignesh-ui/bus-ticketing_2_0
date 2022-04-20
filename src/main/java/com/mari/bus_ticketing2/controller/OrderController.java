package com.mari.bus_ticketing2.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.mari.bus_ticketing2.domain.WebResponse;
import com.mari.bus_ticketing2.services.PropertiesService;
import com.mari.bus_ticketing2.services.RazorPayIntegrationService;
import com.razorpay.Order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderController extends HttpServlet{

    private static final Logger logger=LogManager.getLogger(OrderController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebResponse<Order> webResponse;
        String resString;
        try(
            JsonReader reader=new JsonReader(req.getReader());
        ){
            reader.beginObject();
            //String userId=(reader.nextName().equals("userId"))?reader.nextString():null;
            long amount=(reader.nextName().equals("amount"))?reader.nextLong():0;
            String receipt=(reader.nextName().equals("receipt"))?reader.nextString():null;
            reader.endObject();
            String currency="INR";
            String clientId=PropertiesService.getProperty("CLIENT_ID");
            String clientSecret = PropertiesService.getProperty("CLIENT_SECRET");

            logger.debug("Received payment info: "+amount+" "+receipt);

            Order order=RazorPayIntegrationService.createOrder(clientId, clientSecret, amount, currency, receipt);
            // RazorPayIntegrationService.saveOrder(order,userId);
            webResponse=new WebResponse<Order>(true, "Order Created Successfully", order);
            resp.setStatus(200);

        }catch(Exception e){
            logger.error("Error Creating Order");
            logger.catching(e);
            webResponse=new WebResponse<>(false, "Error Creating Order");
            resp.setStatus(500);
        }
        resString=new Gson().toJson(webResponse);
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Header", "Content-Type");
        resp.setContentType("application/json");
        resp.getWriter().write(resString);
    }
    
}
