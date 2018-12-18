package com.kgcorner.springsecuritydemo.config;

import com.kgcorner.springsecuritydemo.service.BasicAuthenticationProvider;
import com.kgcorner.springsecuritydemo.service.BearerAuthenticationProvider;
import com.kgcorner.springsecuritydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


/**
 * Defines securities configuration
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_URLS = {"/echo", "/login"}; //These URLS should be available publicly
    private static final String[] USER_SECURED_URLS = {"/api/**"}; //These URLS should be accessible by Users and Admin only
    private static final String[] ADMIN_SECURED_URLS = {"/admin/**"}; //These URLS should be accessible by Admin only

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //Adds BasicAuthFilter filter after BasicAuthenticationFilter
                .addFilterAfter(new BasicAuthFilter(), BasicAuthenticationFilter.class)
                //Adds BearerAuthFilter filter after BasicAuthFilter
                .addFilterAfter(new BearerAuthFilter(), BasicAuthFilter.class)
                .authorizeRequests()
                .antMatchers(PUBLIC_URLS).permitAll()
                .antMatchers(USER_SECURED_URLS).hasAnyRole("user", "admin")
                .antMatchers(ADMIN_SECURED_URLS).hasAnyRole("admin");
    }

    /**
     * Configures one or more {@link AuthenticationProvider} which will be used for authenticating user in system
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getBasicAuthenticationProvider())
                .authenticationProvider(getBearerAuthenticationProvider())
                .userDetailsService(myUserDetailsServiceBean());
    }

    /**
     * Creates and returns {@link BasicAuthenticationProvider} for authenticating request with BASIC Token ({@link com.kgcorner.springsecuritydemo.data.BasicAuthToken}
     * @return
     */
    @Bean
    public AuthenticationProvider getBasicAuthenticationProvider() {
        BasicAuthenticationProvider authenticationProvider = new BasicAuthenticationProvider();
        authenticationProvider.setUserDetailsService(myUserDetailsServiceBean());
        return authenticationProvider;
    }

    /**
     * Creates and returns {@link BearerAuthenticationProvider} for authenticating request with BEARER Token ({@link com.kgcorner.springsecuritydemo.data.BearerAuthToken}
     * @return
     */
    @Bean
    public AuthenticationProvider getBearerAuthenticationProvider() {
        BearerAuthenticationProvider authenticationProvider = new BearerAuthenticationProvider();
        authenticationProvider.setUserDetailsService(myUserDetailsServiceBean());
        return authenticationProvider;
    }

    /**
     * Creates bean for {@link UserDetailsService} which will be used for fetching user's detail using username
     * @return
     */
    @Bean
    public UserDetailsService myUserDetailsServiceBean(){
        return userService;
    }
}
