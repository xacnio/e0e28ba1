package com.example.amdscs.api.services;

import com.example.amdscs.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    public CustomUserDetailsService() { }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = new User(email, "$2y$10$Yu2NuJspy1e.lKVZ56AKweqMgOyZXB90Si5x7O20jkVZpWtbA8RaS"); // 123456
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        if(email.contains("admin")) roles.add("ADMIN");

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(roles.toArray(new String[0]))
                .build();
    }
}