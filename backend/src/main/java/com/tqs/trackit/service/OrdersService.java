package com.tqs.trackit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tqs.trackit.model.Order;
import com.tqs.trackit.repository.OrderRepository;

@Service
public class OrdersService {

    @Autowired
    OrderRepository orderRep;

    public Page<Order> getOrders(Integer page) {
        Pageable elements = PageRequest.of(page, 4);
        return orderRep.findAll(elements);
    }

    public Order getOrderById(Long id) {
        return orderRep.findById(id).orElse(null);
    }

    public Order saveOrder(Order order) {
        return orderRep.save(order);
    }

    public Page<Order> getOrdersByRiderId(Long riderId,Integer page) {
        Pageable elements = PageRequest.of(page, 4);
        return orderRep.findByRiderId(riderId,elements);
    }

    public Page<Order> getOrdersByStoreId(Long storeId,Integer page) {
        Pageable elements = PageRequest.of(page, 4);
        return orderRep.findByStoreId(storeId,elements);
    }

    public Page<Order> getOrdersByStatus(String status,Integer page) {
        Pageable elements = PageRequest.of(page, 4);
        return orderRep.findByOrderStatus(status,elements);
    }

    public void deleteOrder(Long orderId) {
        orderRep.deleteById(orderId);
    }
    
}
