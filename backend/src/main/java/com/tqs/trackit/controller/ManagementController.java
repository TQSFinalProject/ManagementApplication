package com.tqs.trackit.controller;

import com.tqs.trackit.exception.ResourceNotFoundException;
import com.tqs.trackit.model.JobApplication;
import com.tqs.trackit.config.TokenProvider;
import com.tqs.trackit.dtos.JobApplicationDTO;
import com.tqs.trackit.dtos.LocationDTO;
import com.tqs.trackit.model.Order;
import com.tqs.trackit.dtos.OrderDTO;
import com.tqs.trackit.model.Rider;
import com.tqs.trackit.model.Store;
import com.tqs.trackit.model.User;
import com.tqs.trackit.service.AuthService;
import com.tqs.trackit.service.JobApplicationsService;
import com.tqs.trackit.service.OrdersService;
import com.tqs.trackit.service.RidersService;
import com.tqs.trackit.service.StoresService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
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

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private AuthService authServ;

    @GetMapping("/orders")
    public ResponseEntity<Page<Order>> getOrders(@RequestParam(required = false) Integer page,@RequestParam(required = false) Long riderId,@RequestParam(required = false) Long storeId,@RequestParam(required = false) String status) {
        if(page==null) 
        {
            page=0;
        }

        return ResponseEntity.ok().body(ordersServ.getOrders(page));
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
    public ResponseEntity<Page<Order>> getOrderByRiderId(@RequestParam(required = false) Integer page, @PathVariable(value = "riderId") Long riderId) {
        if(page==null) {
            page=0;
        }
        Page<Order> ordersByRider = ordersServ.getOrdersByRiderId(riderId,page);
        return ResponseEntity.ok().body(ordersByRider);
    }

    @GetMapping("/orders/store/{storeId}") 
    public ResponseEntity<Page<Order>> getOrderByStoreId(@RequestParam(required = false) Integer page, @PathVariable(value = "storeId") Long storeId) {
        if(page==null) {
            page=0;
        }
        Page<Order> ordersByStore = ordersServ.getOrdersByStoreId(storeId,page);
        return ResponseEntity.ok().body(ordersByStore);
    }

    @GetMapping("/orders/status/{status}") 
    public ResponseEntity<Page<Order>> getOrderByStatus(@RequestParam(required = false) Integer page, @PathVariable(value = "status") String status) {
        if(page==null) {
            page=0;
        }
        Page<Order> ordersByStatus = ordersServ.getOrdersByStatus(status,page);
        return ResponseEntity.ok().body(ordersByStatus);
    }

    @PostMapping("/orders")
    public Order createOrder(@RequestBody OrderDTO order) {
        return ordersServ.saveOrder(order.toOrderEntity());
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable(value = "orderId") String orderId) {
        try {
            Long id = Long.parseLong(orderId);
            ordersServ.deleteOrder(id);
            return ResponseEntity.ok().body("Deleted");
        } 
        catch(NumberFormatException e) { return ResponseEntity.badRequest().body("Not a Valid ID");}
    }

    @GetMapping("/riders")
    public ResponseEntity<Page<Rider>> getRiders(@RequestParam(required = false) Integer page,@RequestParam(required = false) String sort, @RequestParam(required = false) String desc) throws ResourceNotFoundException {
        if(page==null) {
            page=0;
        }
        
        if(sort==null) {
            return ResponseEntity.ok().body(ridersServ.getRiders(page));
        }
        
        switch(sort) {
            case "rating":
                if(desc!=null && desc.equals("true")) {
                    return ResponseEntity.ok().body(ridersServ.getRidersByRating5to0(page));
                }
                return ResponseEntity.ok().body(ridersServ.getRidersByRating0to5(page));

            case "name":
                if(desc!=null && desc.equals("true")) {
                    return ResponseEntity.ok().body(ridersServ.getRidersByNameZtoA(page));
                }
                return ResponseEntity.ok().body(ridersServ.getRidersByNameAtoZ(page));
            
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

    // @PostMapping("/riders")
    // public Rider createRider(@RequestBody RiderDTO rider) {
    //     return ridersServ.saveRider(rider.toRiderEntity());
    // }

    @DeleteMapping("/riders/{riderId}")
    public ResponseEntity<String> deleteRider(@PathVariable(value = "riderId") String riderId) {
        try {
            Long id = Long.parseLong(riderId);
            ridersServ.deleteRider(id);
            return ResponseEntity.ok().body("Deleted");
        } 
        catch(NumberFormatException e) { return ResponseEntity.badRequest().body("Not a Valid ID");}
    }

    @GetMapping("/stores")
    public ResponseEntity<Page<Store>> getStores(@RequestParam(required = false) Integer page) {
        if(page==null) {
            page=0;
        }
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

    @GetMapping("/stores/name/{storeName}") 
    public ResponseEntity<Store> getStoreByName(@PathVariable(value = "storeName") String storeName) {
        Store storeByName = storesServ.getStoreByName(storeName);
        return ResponseEntity.ok().body(storeByName);
    }

    @GetMapping("/stores/address/{storeAddress}") 
    public ResponseEntity<Store> getStoreByAddress(@PathVariable(value = "storeAddress") String storeAddress) {
        Store storeByAddress = storesServ.getStoreByAddress(storeAddress);
        return ResponseEntity.ok().body(storeByAddress);
    }

    // @PostMapping("/stores")
    // public ResponseEntity<Store> createStore(@RequestBody StoreDTO store) {
    //     Store otherStore = storesServ.getStoreByName(store.getStoreName());
    //     if(otherStore != null) return ResponseEntity.status(409).body(otherStore);
    //     return ResponseEntity.ok().body(storesServ.saveStore(store.toStoreEntity()));
    // }

    @DeleteMapping("/stores/{storeId}")
    public ResponseEntity<String> deleteStore(@PathVariable(value = "storeId") String storeId) {
        try {
            Long id = Long.parseLong(storeId);
            storesServ.deleteStore(id);
            return ResponseEntity.ok().body("Deleted");
        } 
        catch(NumberFormatException e) { return ResponseEntity.badRequest().body("Not a Valid ID");}
    }

    @GetMapping("/jobApplications")
    public ResponseEntity<Page<JobApplication>> getApplications(@RequestParam(required = false) Integer page) {
        if(page==null) {
            page=0;
        }
        return ResponseEntity.ok().body(jobServ.getApplications(page));
    }

    @GetMapping("/jobApplications/{jobApplicationId}") 
    public ResponseEntity<JobApplication> getJobApplicationById(@PathVariable(value = "jobApplicationId") Long jobApplicationId)
        throws ResourceNotFoundException {
        JobApplication jobApplication1 = jobServ.getApplicationById(jobApplicationId);
        if(jobApplication1==null) {
            throw new ResourceNotFoundException("JobApplication not found for this id :: " + jobApplicationId);
        }
        return ResponseEntity.ok().body(jobApplication1);
    }

    @PostMapping("/jobApplications")
    public JobApplication createApplication(@RequestBody JobApplicationDTO application) {
        return jobServ.saveApplication(application.toJobApplicationEntity());
    }

    @DeleteMapping("/jobApplications/{jobApplicationId}")
    public ResponseEntity<String> deleteJobApplication(@PathVariable(value = "jobApplicationId") String jobAppId) {
        try {
            Long id = Long.parseLong(jobAppId);
            jobServ.deleteApplication(id);
            return ResponseEntity.ok().body("Deleted");
        } 
        catch(NumberFormatException e) { return ResponseEntity.badRequest().body("Not a Valid ID");}
    }

    // Endpoints that require authentication

    @GetMapping("/rider/orders")
    public ResponseEntity<List<Map<String,Object>>> getRiderOrdersByDistance(@RequestHeader("authorization") String auth, @RequestParam(required = false) Integer limit) {
        String token = auth.split(" ")[1];
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = authServ.getUserByUsername(username);
        Rider rider = authServ.getRiderByUser(user);
        if(rider == null) return ResponseEntity.status(401).build();
        List<Map<String,Object>> sortedOrder = ordersServ.getRiderOrders(rider, limit);
        return ResponseEntity.ok().body(sortedOrder);
    }

    @PostMapping("/rider/updateLocation")
    public ResponseEntity<LocationDTO> updateRiderLocation(@RequestHeader("authorization") String auth, @RequestBody LocationDTO location) {
        String token = auth.split(" ")[1];
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = authServ.getUserByUsername(username);
        Rider rider = authServ.getRiderByUser(user);
        if(rider == null) return ResponseEntity.status(401).build();
        return ResponseEntity.ok().body(ridersServ.updateRiderLocation(rider, location));
    }

    @PutMapping("/rider/order/{orderid}")
    public ResponseEntity<Order> riderAcceptsOrder(@RequestHeader("authorization") String auth, @PathVariable Long orderid) {
        String token = auth.split(" ")[1];
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = authServ.getUserByUsername(username);
        Rider rider = authServ.getRiderByUser(user);
        if(rider == null) return ResponseEntity.status(401).build();
        return null;
    }

}
