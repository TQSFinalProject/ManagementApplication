package com.tqs.trackit.repository;

import com.tqs.trackit.model.Rider;
import com.tqs.trackit.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiderRepository extends JpaRepository<Rider,Long>{
    Page<Rider> findAll(Pageable page);
    Rider findByUser_Username(String username);
    Rider findByUser(User user);
}
