package com.review.crud.Repository;

import com.review.crud.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByUserName(String userName);
    public Optional<User> findByEmail(String email);
}
