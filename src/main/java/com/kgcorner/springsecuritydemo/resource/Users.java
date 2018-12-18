package com.kgcorner.springsecuritydemo.resource;

import com.google.gson.Gson;
import com.kgcorner.springsecuritydemo.data.User;
import com.kgcorner.springsecuritydemo.service.UserService;
import com.kgcorner.springsecuritydemo.utility.JWTUtility;
import com.kgcorner.springsecuritydemo.utility.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
public class Users {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(@RequestHeader("Authorization") String basicToken) {

        if(Strings.isNullOrEmpty(basicToken)) {
            throw new IllegalArgumentException("token cant be empty");
        }
        if(basicToken.startsWith("basic ") || basicToken.startsWith("Basic ") || basicToken.startsWith("BASIC ") ) {
            String decodedToken = new String(Base64.getDecoder().decode(basicToken.split(" ")[1]));
            String username = decodedToken.split(":")[0];
            String password = decodedToken.split(":")[1];
            User user = userService.authenticateUser(username, password);
            return JWTUtility.createJWTToken(new Gson().toJson(user));
        }
        throw new IllegalArgumentException("basic token is needed");
    }

    @GetMapping("/echo")
    public String echo(@RequestParam("message") String message) {
        return "Echo:" + message;
    }

}
