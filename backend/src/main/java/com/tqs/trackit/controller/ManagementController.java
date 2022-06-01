package com.tqs.trackit.controller;

import java.util.List;

import com.tqs.trackit.model.Order;
import com.tqs.trackit.model.Rider;
import com.tqs.trackit.model.Store;
import com.tqs.trackit.service.ManagementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ManagementController {

    @Autowired
    private ManagementService manageServ;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok().body(manageServ.getOrders());
    }

    @PostMapping("/orders")
    public Order createOrder(@RequestBody Order order) {
        return manageServ.saveOrder(order);
    }

    @GetMapping("/riders")
    public ResponseEntity<List<Rider>> getRiders() {
        return ResponseEntity.ok().body(manageServ.getRiders());
    }

    @PostMapping("/riders")
    public Rider createRider(@RequestBody Rider rider) {
        return manageServ.saveRider(rider);
    }

    @GetMapping("/stores")
    public ResponseEntity<List<Store>> getStores() {
        return ResponseEntity.ok().body(manageServ.getStores());
    }

    @PostMapping("/stores")
    public Store createStore(@RequestBody Store store) {
        return manageServ.saveStore(store);
    }
}