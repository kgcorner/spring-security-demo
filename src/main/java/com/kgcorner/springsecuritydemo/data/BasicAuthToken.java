package com.kgcorner.springsecuritydemo.data;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

public class BasicAuthToken extends UsernamePasswordAuthenticationToken {

    public BasicAuthToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public BasicAuthToken(Object principal, Object credentials,
                          Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

}
