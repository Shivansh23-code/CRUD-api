package com.review.crud.dto;

import com.review.crud.controller.AuthController;
import lombok.Data;

@Data
public class AuthResponse {

    private String accessToken;
    private String refreshToken;

    public AuthResponse(String token) {
        this.accessToken = token;
    }

    public AuthResponse(String token, String refreshToken) {
        this.accessToken = token;
        this.refreshToken = refreshToken;
    }

}
