package com.kgcorner.springsecuritydemo.service;

import com.google.gson.Gson;
import com.kgcorner.springsecuritydemo.data.BasicAuthToken;
import com.kgcorner.springsecuritydemo.data.BearerAuthToken;
import com.kgcorner.springsecuritydemo.data.User;
import com.kgcorner.springsecuritydemo.utility.JWTUtility;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BearerAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        BearerAuthToken bearerAuthToken = (BearerAuthToken) authentication;
        Object payload = JWTUtility.validateToken(bearerAuthToken.getPrincipal().toString());
        if (payload != null) {
            String userPayload = payload.toString();
            User user = new Gson().fromJson(userPayload, User.class);
            bearerAuthToken = new BearerAuthToken(user.getAuthorities(), user);
            return bearerAuthToken;
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return BearerAuthToken.class.isAssignableFrom(authentication);
    }
}
