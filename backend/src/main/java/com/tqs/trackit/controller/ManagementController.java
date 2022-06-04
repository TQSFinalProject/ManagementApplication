package com.tqs.trackit.controller;

import java.util.List;

import com.tqs.trackit.model.JobApplication;
import com.tqs.trackit.model.Order;
import com.tqs.trackit.model.Rider;
import com.tqs.trackit.model.Store;
import com.tqs.trackit.service.JobApplicationsService;
import com.tqs.trackit.service.OrdersService;
import com.tqs.trackit.service.RidersService;
import com.tqs.trackit.service.StoresService;

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
    private OrdersService ordersServ;

    @Autowired
    private RidersService ridersServ;

    @Autowired
    private StoresService storesServ;

    @Autowired
    private JobApplicationsService jobServ;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok().body(ordersServ.getOrders());
    }

    @PostMapping("/orders")
    public Order createOrder(@RequestBody Order order) {
        return ordersServ.saveOrder(order);
    }

    @GetMapping("/riders")
    public ResponseEntity<List<Rider>> getRiders() {
        return ResponseEntity.ok().body(ridersServ.getRiders());
    }

    @PostMapping("/riders")
    public Rider createRider(@RequestBody Rider rider) {
        return ridersServ.saveRider(rider);
    }

    @GetMapping("/stores")
    public ResponseEntity<List<Store>> getStores() {
        return ResponseEntity.ok().body(storesServ.getStores());
    }

    @PostMapping("/stores")
    public Store createStore(@RequestBody Store store) {
        return storesServ.saveStore(store);
    }

    @GetMapping("/job_applications")
    public ResponseEntity<List<JobApplication>> getApplications() {
        return ResponseEntity.ok().body(jobServ.getApplications());
    }

    @PostMapping("/job_applications")
    public JobApplication createApplication(@RequestBody JobApplication application) {
        return jobServ.saveApplication(application);
    }
}
