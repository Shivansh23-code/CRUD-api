package com.review.crud.Repository;

import com.review.crud.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String r_token) ;
    void deleteByUserName(String userName);
}
