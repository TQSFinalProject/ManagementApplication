package com.tqs.trackit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tqs.trackit.model.Order;
import com.tqs.trackit.repository.OrderRepository;

@Service
public class OrdersService {

    @Autowired
    OrderRepository orderRep;

    public List<Order> getOrders() {
        return orderRep.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRep.findById(id).orElse(null);
    }

    public Order saveOrder(Order order) {
        return orderRep.save(order);
    }
    
}
