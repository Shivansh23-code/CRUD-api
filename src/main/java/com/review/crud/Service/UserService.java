package com.review.crud.Service;

import com.review.crud.Entity.User;
import com.review.crud.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.review.crud.annotation.ProfileExecution;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    Create User
    public User createUser(User user){
        return userRepository.save(user);
    }

    //    Get User by id, username, email and get All users
    @ProfileExecution
    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }


    public User getUserByUsername(String username) {
        return userRepository.findByUserName(username).orElse(null);
    }


    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email Not Found"));
    }


    @ProfileExecution
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }


//    Update Users

    @ProfileExecution
    public User updateUser(Long id, User updatedUser){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        user.setUserName(updatedUser.getUserName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

        return userRepository.save(user);
    }


//    Delete Users by Id
    @ProfileExecution
    public String deleteUserById(Long id){
        userRepository.deleteById(id);
        return "deleted";
    }
}