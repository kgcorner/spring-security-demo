package com.kgcorner.springsecuritydemo.config;

import com.kgcorner.springsecuritydemo.data.BasicAuthToken;
import com.kgcorner.springsecuritydemo.utility.Strings;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;

public class BasicAuthFilter extends HttpFilter {

    private static final String AUTHENTICATION_HEADER = "Authorization";
    private static final String SCHEME_PREFIX = "basic ";
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String authHeader = ((HttpServletRequest)request).getHeader(AUTHENTICATION_HEADER);
        if(Strings.isNullOrEmpty(authHeader) || !authHeader.toLowerCase().startsWith(SCHEME_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        String decoded = new String(Base64.getDecoder().decode(authHeader.substring(SCHEME_PREFIX.length())));
        String username = decoded.split(":")[0];
        String password = decoded.split(":")[1];
        BasicAuthToken basicAuthToken = new BasicAuthToken(username, password);
        SecurityContextHolder.getContext().setAuthentication(basicAuthToken);
        chain.doFilter(request, response);
    }
}
