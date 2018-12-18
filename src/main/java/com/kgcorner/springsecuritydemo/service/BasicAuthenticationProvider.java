package com.kgcorner.springsecuritydemo.service;

import com.kgcorner.springsecuritydemo.data.BasicAuthToken;
import com.kgcorner.springsecuritydemo.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class BasicAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        BasicAuthToken basicAuthToken = (BasicAuthToken) authentication;
        User user = userService.authenticateUser(basicAuthToken.getPrincipal().toString(),
                basicAuthToken.getCredentials().toString());
        if(user != null)
            return new BasicAuthToken(user, "***", user.getAuthorities());
        else
            return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return BasicAuthToken.class.isAssignableFrom(aClass);
    }
}
