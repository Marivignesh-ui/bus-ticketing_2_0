package com.mari.bus_ticketing2.services;

// import com.mari.repository.OrderRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class RazorPayIntegrationService {
    
    private static final Logger logger=LogManager.getLogger(RazorPayIntegrationService.class);

    public static Order createOrder(String clientId, String clientSecret, long amount, String currency, String receipt) {
        Order order = null;
        try {
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount); // amount in the smallest currency unit
            orderRequest.put("currency", currency);
            orderRequest.put("receipt", receipt);
            logger.info("orderRequest: " + orderRequest);

            RazorpayClient razorpay = new RazorpayClient(clientId, clientSecret);
            order = razorpay.Orders.create(orderRequest);
            System.out.println(order);
            logger.info("order: " + order);

        } catch (RazorpayException e) {
            System.out.println(e.getMessage());
            logger.error("Error creating order..");
            logger.error(e);
        }
        return order;
    }
}
