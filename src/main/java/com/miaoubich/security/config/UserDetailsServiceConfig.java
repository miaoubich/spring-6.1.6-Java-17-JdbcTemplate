package com.miaoubich.security.config;

import com.miaoubich.security.service.dao.UserDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class UserDetailsServiceConfig {

    private final UserDAO userDAO;

    public UserDetailsServiceConfig(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userDAO.findByUsername(username)
                                                            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
