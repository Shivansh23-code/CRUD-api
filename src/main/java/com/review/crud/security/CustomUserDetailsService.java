package com.review.crud.security;

import com.review.crud.Entity.User;
import com.review.crud.Repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import com.review.crud.annotation.ProfileExecution;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @ProfileExecution
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                Collections.emptyList()
        );
    }
}