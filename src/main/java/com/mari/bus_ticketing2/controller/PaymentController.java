package com.mari.bus_ticketing2.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;

import com.mari.bus_ticketing2.domain.PaymentResponse;
import com.mari.bus_ticketing2.domain.Ticket;
import com.mari.bus_ticketing2.domain.WebResponse;
import com.mari.bus_ticketing2.services.PropertiesService;
import com.mari.bus_ticketing2.services.TicketService;

import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booktickets")
public class PaymentController {

    private static final Logger logger=LogManager.getLogger(PaymentController.class);

    @Autowired
    private TicketService ticketService;

    @PostMapping
    protected WebResponse<List<Ticket>> doPost(@RequestParam String razorpay_payment_id,
                @RequestParam String razorpay_order_id,
                @RequestParam String razorpay_signature,
                @RequestBody List<Ticket> tickets, HttpServletResponse resp){

        boolean isSignatureValid = isSignatureValid(razorpay_order_id, razorpay_payment_id, razorpay_signature);
        WebResponse<List<Ticket>> webResponse=null;
        if(isSignatureValid){
            try {
                logger.debug("Request to book tickets received successfully!!");
                List<Ticket> ticketsfromDB=null;
                logger.info("********-------------Request Object read Successfully-----------*******:"+tickets);
                logger.info("!! About to call bookTicketService");
                ticketsfromDB=ticketService.bookTicketService(tickets);
                resp.setHeader("Access-Control-Allow-Origin", "*");
                resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                resp.setHeader("Access-Control-Allow-Header", "Content-Type");
                resp.setStatus(200);
                resp.setContentType("application/json");
                webResponse=new WebResponse<List<Ticket>>(true,"ticket booked successfully",ticketsfromDB);
            }catch(Exception e){
                logger.error(e);
                logger.catching(e);
                resp.setStatus(500);
                resp.setHeader("Access-Control-Allow-Origin", "*");
                resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                resp.setHeader("Access-Control-Allow-Header", "Content-Type");
                webResponse=new WebResponse<List<Ticket>>(false,"ticket booking failed");
            }   
            return webResponse; 
        }

        webResponse=new WebResponse(false,"payment failed",new PaymentResponse(razorpay_payment_id, razorpay_order_id, isSignatureValid));
        return webResponse;
    }

    static boolean isSignatureValid(String razorpay_order_id, String razorpay_payment_id, String razorpay_signature) {
        String CLIENT_SECRET = PropertiesService.getProperty("CLIENT_SECRET");
        boolean isSignatureValid = false;

        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            sha256_HMAC.init(new SecretKeySpec(CLIENT_SECRET.getBytes(), "HmacSHA256"));

            String data = razorpay_order_id + "|" + razorpay_payment_id;
            byte[] hmacSha256 = sha256_HMAC.doFinal(data.getBytes());
            String generated_signature = Hex.encodeHexString(hmacSha256);

            logger.info("generatedSignature: " + generated_signature);
            logger.info("razorpay_signature: " + razorpay_signature);

            System.out.println("generatedSignature: " + generated_signature);
            System.out.println("razorpay_signature: " + razorpay_signature);

            if (generated_signature.equals(razorpay_signature)) {
                isSignatureValid = true;
            }

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            logger.catching(e);
            logger.error(e.getMessage());
        }
        System.out.println("isSignatureValid: " + isSignatureValid);
        return isSignatureValid;
    }

    // @Override
    // protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    //     res.setHeader("Access-Control-Allow-Origin", "*");
    //     res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");   
    //     res.setHeader("Access-Control-Allow-Header", "Content-Type");
    // }
}
