package com.tqs.trackit.controller;

import java.util.List;

import com.tqs.trackit.exception.ResourceNotFoundException;
import com.tqs.trackit.model.JobApplication;
import com.tqs.trackit.dtos.JobApplicationDTO;
import com.tqs.trackit.model.Order;
import com.tqs.trackit.dtos.OrderDTO;
import com.tqs.trackit.model.Rider;
import com.tqs.trackit.dtos.RiderDTO;
import com.tqs.trackit.model.Store;
import com.tqs.trackit.dtos.StoreDTO;
import com.tqs.trackit.service.JobApplicationsService;
import com.tqs.trackit.service.OrdersService;
import com.tqs.trackit.service.RidersService;
import com.tqs.trackit.service.StoresService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:3001")
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

    @GetMapping("/orders/{orderId}") 
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "orderId") Long orderId)
        throws ResourceNotFoundException {
        Order order1 = ordersServ.getOrderById(orderId);
        if(order1==null) {
            throw new ResourceNotFoundException("Order not found for this id :: " + orderId);
        }
        return ResponseEntity.ok().body(order1);
    }

    @GetMapping("/orders/rider/{riderId}") 
    public ResponseEntity<List<Order>> getOrderByRiderId(@PathVariable(value = "riderId") Long riderId) {
        List<Order> ordersByRider = ordersServ.getOrdersByRiderId(riderId);
        return ResponseEntity.ok().body(ordersByRider);
    }

    @PostMapping("/orders")
    public Order createOrder(@RequestBody OrderDTO order) {
        return ordersServ.saveOrder(order.toOrderEntity());
    }

    @GetMapping("/riders")
    public ResponseEntity<Page<Rider>> getRiders(@RequestParam(required = false) String sort, @RequestParam(required = false) String desc) throws ResourceNotFoundException {
        if(sort==null) {
            return ResponseEntity.ok().body(ridersServ.getRiders(0));
        }
        
        switch(sort) {
            case "rating":
                if(desc!=null && desc.equals("true")) {
                    return ResponseEntity.ok().body(ridersServ.getRidersByRating5to0(0));
                }
                return ResponseEntity.ok().body(ridersServ.getRidersByRating0to5(0));

            case "name":
                if(desc!=null && desc.equals("true")) {
                    return ResponseEntity.ok().body(ridersServ.getRidersByNameZtoA(0));
                }
                return ResponseEntity.ok().body(ridersServ.getRidersByNameAtoZ(0));
            
            default:
                throw new ResourceNotFoundException("Not a filter :: " + sort);
                
        }
    }

    @GetMapping("/riders/{riderId}") 
    public ResponseEntity<Rider> getRiderById(@PathVariable(value = "riderId") Long riderId)
        throws ResourceNotFoundException {
        Rider rider1 = ridersServ.getRiderById(riderId);
        if(rider1==null) {
            throw new ResourceNotFoundException("Rider not found for this id :: " + riderId);
        }
        return ResponseEntity.ok().body(rider1);
    }



    // @GetMapping("/riders/sortByRating") 
    // public ResponseEntity<List<Rider>> getRidersByRatingMean() {
    //     return ResponseEntity.ok().body(ridersServ.getRidersByRating());
    // }

    // @GetMapping("/riders/sortByName") 
    // public ResponseEntity<List<Rider>> getRidersAlphabetically() {
    //     return ResponseEntity.ok().body(ridersServ.getRidersAlphabetically());
    // }

    @PostMapping("/riders")
    public Rider createRider(@RequestBody RiderDTO rider) {
        return ridersServ.saveRider(rider.toRiderEntity());
    }

    @GetMapping("/stores")
    public ResponseEntity<Page<Store>> getStores(@RequestParam(required = false) Integer page) {
        return ResponseEntity.ok().body(storesServ.getStores(page));
    }

    @GetMapping("/stores/{storeId}") 
    public ResponseEntity<Store> getStoreById(@PathVariable(value = "storeId") Long storeId)
        throws ResourceNotFoundException {
        Store store1 = storesServ.getStoreById(storeId);
        if(store1==null) {
            throw new ResourceNotFoundException("Store not found for this id :: " + storeId);
        }
        return ResponseEntity.ok().body(store1);
    }

    @PostMapping("/stores")
    public Store createStore(@RequestBody StoreDTO store) {
        return storesServ.saveStore(store.toStoreEntity());
    }

    @GetMapping("/job_applications")
    public ResponseEntity<List<JobApplication>> getApplications() {
        return ResponseEntity.ok().body(jobServ.getApplications());
    }

    @GetMapping("/job_applications/{jobApplicationId}") 
    public ResponseEntity<JobApplication> getJobApplicationById(@PathVariable(value = "jobApplicationId") Long jobApplicationId)
        throws ResourceNotFoundException {
        JobApplication jobApplication1 = jobServ.getApplicationById(jobApplicationId);
        if(jobApplication1==null) {
            throw new ResourceNotFoundException("JobApplication not found for this id :: " + jobApplicationId);
        }
        return ResponseEntity.ok().body(jobApplication1);
    }

    @PostMapping("/job_applications")
    public JobApplication createApplication(@RequestBody JobApplicationDTO application) {
        return jobServ.saveApplication(application.toJobApplicationEntity());
    }
}
