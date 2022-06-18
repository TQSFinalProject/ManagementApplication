package com.tqs.trackit.repository;

import com.tqs.trackit.model.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>{
    public Page<Order> findByRiderId(Long riderId,Pageable pageable);
}
