package com.review.crud.security;

import com.review.crud.Entity.RefreshToken;
import com.review.crud.Repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, JwtUtil jwtUtil) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtUtil = jwtUtil;
    }

    public RefreshToken createRefreshToken(String userName){
        String token = jwtUtil.generateRefreshToken(userName);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);;
        refreshToken.setUserName(userName);
        refreshToken.setExpiryDate(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7)));

        return refreshTokenRepository.save(refreshToken);
    }

    public boolean validateRefreshToken(String token){
        RefreshToken stored = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if(stored.getExpiryDate().before(new Date())){
            refreshTokenRepository.delete(stored);
            throw new RuntimeException("Refresh token expired");
        }
        return true;
    }
}

