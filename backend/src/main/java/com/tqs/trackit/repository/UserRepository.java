package com.tqs.trackit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tqs.trackit.model.User;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);
    
}
