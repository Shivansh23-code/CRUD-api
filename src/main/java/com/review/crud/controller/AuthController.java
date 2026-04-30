package com.review.crud.controller;

import com.review.crud.Entity.User;
import com.review.crud.Service.UserService;
import com.review.crud.dto.AuthResponse;
import com.review.crud.dto.RefreshRequest;
import com.review.crud.dto.UserLoginDto;
import com.review.crud.security.JwtUtil;
import com.review.crud.security.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UserService userService,
                          PasswordEncoder passwordEncoder,
                          RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {

        System.out.println("Username: " + userLoginDto.getName());
        System.out.println("Password: " + userLoginDto.getPassword());

        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDto.getName(),
                            userLoginDto.getPassword()
                    )
            );

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid Username or Password");
        }

        String username = authentication.getName();

        String accessToken = jwtUtil.generateAccessToken(username);

        String refreshToken = refreshTokenService
                .createRefreshToken(username)
                .getToken();

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){

        if(userService.getUserByUsername(user.getUserName()) != null){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Username is already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userService.createUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser);
    }


    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshRequest refreshRequest){

        String refreshToken = refreshRequest.getRefreshToken();

        refreshTokenService.validateRefreshToken(refreshToken);

        String userName = jwtUtil.extractUsername(refreshToken);

        String newAccessToken = jwtUtil.generateAccessToken(userName);

        return new AuthResponse(newAccessToken, refreshToken);
    }
}