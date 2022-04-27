package com.mari.bus_ticketing2.controller;

import javax.servlet.http.HttpServletResponse;

import com.mari.bus_ticketing2.domain.User;
import com.mari.bus_ticketing2.domain.WebResponse;
import com.mari.bus_ticketing2.services.LoginService;
import com.mari.bus_ticketing2.util.HashUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
public class LoginController{
    
    private static final Logger logger=LogManager.getLogger();

    @Autowired
    private LoginService loginService;

    @PostMapping
    public WebResponse<User> doPost(@RequestBody User user,HttpServletResponse res) {
        WebResponse<User> webResponse=null;
        try{
            
            String hashedPassword=HashUtil.hash(user.getPassword().toCharArray());
            User userfromDB=loginService.login(user.getEmail());

            logger.debug("User returned from DB: "+userfromDB+" Given hashedpassword: "+hashedPassword+"User pass:"+userfromDB.getHashedPassword());

            res.setContentType("application/json");
            if(user!=null && (hashedPassword).equals(userfromDB.getHashedPassword())){
                userfromDB.setHashedPassword(null);
                webResponse=new WebResponse<User>(true, "Login Success",userfromDB);
            }else{
                webResponse=new WebResponse<User>(false, "Invalid credentials");
            }
            res.setStatus(200);
        }catch(Exception e){
            logger.catching(e);
            logger.error("Error login to app: " + e.getMessage());
            res.setStatus(500);
            webResponse = new WebResponse<>(false, "Invalid Credentials");
        }
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        res.setHeader("Access-Control-Allow-Header", "Content-Type");
        res.setContentType("application/json");
        return webResponse;
        
    }

    // @Override
    // protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    //     res.setHeader("Access-Control-Allow-Origin", "*");
    //     res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");   
    //     res.setHeader("Access-Control-Allow-Header", "Content-Type");
    // }
}
