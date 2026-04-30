package com.review.crud.Service;

import com.review.crud.Entity.User;
import com.review.crud.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUserName(username).orElse(null);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email Not Found"));
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public String deleteUserById(Long id){
        userRepository.deleteById(id);
        return "deleted";
    }

    public User updateUser(Long id, User updatedUser){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        user.setUserName(updatedUser.getUserName());
        user.setEmail(updatedUser.getEmail());

        // ✅ FIXED: Always encode password
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

        return userRepository.save(user);
    }
}