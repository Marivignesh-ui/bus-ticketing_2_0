package com.mari.bus_ticketing2.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.mari.bus_ticketing2.domain.User;
import com.mari.bus_ticketing2.domain.WebResponse;
import com.mari.bus_ticketing2.services.LoginService;
import com.mari.bus_ticketing2.util.HashUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet{
    
    private static final Logger logger=LogManager.getLogger();

    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException{
        try{
            JsonReader reader=new JsonReader(req.getReader());
            reader.beginObject();
            String email=(reader.nextName().equals("email"))?reader.nextString():null;
            String password=(reader.nextName().equals("password"))?reader.nextString():null;
            reader.endObject();

            String hashedPassword=HashUtil.hash(password.toCharArray());
            User user=new LoginService().login(email);

            logger.debug("User returned from DB: "+user+" Given hashedpassword: "+hashedPassword+"User pass:"+user.getHashedPassword());

            res.setContentType("application/json");
            WebResponse<User> webResponse=null;
            if(user!=null && (hashedPassword).equals(user.getHashedPassword())){
                user.setHashedPassword(null);
                webResponse=new WebResponse<User>(true, "Login Success",user);
            }else{
                webResponse=new WebResponse<User>(false, "Inalid credentials");
            }
            res.setStatus(200);
            res.setHeader("Access-Control-Allow-Origin", "*");
            res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            res.setHeader("Access-Control-Allow-Header", "Content-Type");
            res.getWriter().write(new Gson().toJson(webResponse));
        }catch(Exception e){
            logger.catching(e);
            logger.error("Error login to app: " + e.getMessage());

            res.setContentType("application/json");
            res.setStatus(500);
            WebResponse<Object> webResponse = new WebResponse<>(false, "Invalid Credentials");
            res.getWriter().write(new Gson().toJson(webResponse));
        }
        
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");   
        res.setHeader("Access-Control-Allow-Header", "Content-Type");
    }
}
