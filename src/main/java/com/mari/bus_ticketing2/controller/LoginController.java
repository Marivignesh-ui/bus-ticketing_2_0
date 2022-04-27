package com.mari.bus_ticketing2.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mari.bus_ticketing2.domain.User;
import com.mari.bus_ticketing2.domain.WebResponse;
import com.mari.bus_ticketing2.services.LoginService;
import com.mari.bus_ticketing2.util.HashUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/login")
public class LoginController{
    
    private static final Logger logger=LogManager.getLogger();

    @Autowired
    private LoginService loginService;

    @PostMapping
    public void doPost(@ModelAttribute("user")User user,HttpServletResponse res)throws IOException{
        try{
            // JsonReader reader=new JsonReader(req.getReader());
            // reader.beginObject();
            // String email=(reader.nextName().equals("email"))?reader.nextString():null;
            // String password=(reader.nextName().equals("password"))?reader.nextString():null;
            // reader.endObject();

            String hashedPassword=HashUtil.hash(user.getPassword().toCharArray());
            User userfromDB=loginService.login(user.getEmail());

            logger.debug("User returned from DB: "+userfromDB+" Given hashedpassword: "+hashedPassword+"User pass:"+userfromDB.getHashedPassword());

            res.setContentType("application/json");
            WebResponse<User> webResponse=null;
            if(user!=null && (hashedPassword).equals(userfromDB.getHashedPassword())){
                userfromDB.setHashedPassword(null);
                webResponse=new WebResponse<User>(true, "Login Success",userfromDB);
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

    // @Override
    // protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    //     res.setHeader("Access-Control-Allow-Origin", "*");
    //     res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");   
    //     res.setHeader("Access-Control-Allow-Header", "Content-Type");
    // }
}
