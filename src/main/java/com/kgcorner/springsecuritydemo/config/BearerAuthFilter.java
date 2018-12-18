package com.kgcorner.springsecuritydemo.config;

import com.google.gson.Gson;
import com.kgcorner.springsecuritydemo.data.BasicAuthToken;
import com.kgcorner.springsecuritydemo.data.BearerAuthToken;
import com.kgcorner.springsecuritydemo.data.User;
import com.kgcorner.springsecuritydemo.utility.JWTUtility;
import com.kgcorner.springsecuritydemo.utility.Strings;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BearerAuthFilter extends HttpFilter {
    private static final String AUTHENTICATION_HEADER = "Authorization";
    private static final String SCHEME_PREFIX = "bearer ";
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String authHeader = ((HttpServletRequest)request).getHeader(AUTHENTICATION_HEADER);
        if(Strings.isNullOrEmpty(authHeader) || !authHeader.toLowerCase().startsWith(SCHEME_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        String bearerTokenStr = authHeader.substring(SCHEME_PREFIX.length());
        BearerAuthToken token = new BearerAuthToken(null, bearerTokenStr);
        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }
}
