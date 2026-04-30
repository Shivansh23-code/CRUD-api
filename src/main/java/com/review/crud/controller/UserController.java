package com.review.crud.controller;

import com.review.crud.Entity.User;
import com.review.crud.Service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PutMapping("/id/{id}")
    public User updateUser( @PathVariable Long id, @RequestBody User user){
        return userService.updateUser(id, user);
    }

    @GetMapping
    public List<User> findAllUser(){
        return userService.getAllUsers();
    }

    @GetMapping("/id/{id}")
    public User findUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/username/{username}")
    public User findUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }

    @GetMapping("/email/{email}")
    public User findUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
    }

    @DeleteMapping("/id/{id}")
    public void deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
    }
}
