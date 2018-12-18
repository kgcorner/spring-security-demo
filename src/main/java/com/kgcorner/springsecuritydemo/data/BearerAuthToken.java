package com.kgcorner.springsecuritydemo.data;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class BearerAuthToken extends AbstractAuthenticationToken {


    private final Object principle;

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public BearerAuthToken(Collection<? extends GrantedAuthority> authorities, Object principle) {
        super(authorities);
        this.principle = principle;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principle;
    }
}
