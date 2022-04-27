package com.mari.bus_ticketing2.controller;

import javax.servlet.http.HttpServletResponse;

import com.mari.bus_ticketing2.domain.OrderDto;
import com.mari.bus_ticketing2.domain.WebResponse;
import com.mari.bus_ticketing2.services.PropertiesService;
import com.mari.bus_ticketing2.services.RazorPayIntegrationService;
import com.razorpay.Order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/order")
public class OrderController{

    private static final Logger logger=LogManager.getLogger(OrderController.class);

    @PostMapping
    protected WebResponse<Order> doPost(@RequestBody OrderDto orderDto, HttpServletResponse resp) {
        WebResponse<Order> webResponse;
        try{
            String currency="INR";
            String clientId=PropertiesService.getProperty("CLIENT_ID");
            String clientSecret = PropertiesService.getProperty("CLIENT_SECRET");

            logger.debug("Received payment info: "+orderDto.getAmount()+" "+orderDto.getReceipt());

            Order order=RazorPayIntegrationService.createOrder(clientId, clientSecret, orderDto.getAmount(), currency, orderDto.getReceipt());
            webResponse=new WebResponse<Order>(true, "Order Created Successfully", order);
            resp.setStatus(200);

        }catch(Exception e){
            logger.error("Error Creating Order");
            logger.catching(e);
            webResponse=new WebResponse<>(false, "Error Creating Order");
            resp.setStatus(500);
        }
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Header", "Content-Type");
        resp.setContentType("application/json");
        return webResponse;
    }
    
}
