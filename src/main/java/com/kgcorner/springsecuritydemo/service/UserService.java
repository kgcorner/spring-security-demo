package com.kgcorner.springsecuritydemo.service;

import com.kgcorner.springsecuritydemo.data.User;
import com.kgcorner.springsecuritydemo.data.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDataRepository userDataRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userDataRepository.getUserByUserName(userName);
    }



    public User authenticateUser(String userName, String password) {
        User user = userDataRepository.getUserByUserName(userName);
        if(user != null) {
            if(user.getPassword().equals(password))
                return user;
        }
        return null;
    }
}
