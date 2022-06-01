package com.tqs.trackit.service;

import java.util.List;

import com.tqs.trackit.model.Job_Application;
import com.tqs.trackit.model.Order;
import com.tqs.trackit.model.Rider;
import com.tqs.trackit.model.Store;
import com.tqs.trackit.repository.Job_ApplicationRepository;
import com.tqs.trackit.repository.OrderRepository;
import com.tqs.trackit.repository.RiderRepository;
import com.tqs.trackit.repository.StoreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagementService {
    
    @Autowired
    OrderRepository orderRep;
    
    @Autowired
    RiderRepository riderRep;

    @Autowired
    StoreRepository storeRep;

    @Autowired
    Job_ApplicationRepository jobRep;

    public List<Order> getOrders() {
        return orderRep.findAll();
    }

    public List<Rider> getRiders() {
        return riderRep.findAll();
    }

    public List<Store> getStores() {
        return storeRep.findAll();
    }

    public List<Job_Application> getApplications() {
        return jobRep.findAll();
    }

    public Order saveOrder(Order order) {
        return orderRep.save(order);
    }

    public Rider saveRider(Rider rider) {
        return riderRep.save(rider);
    }

    public Store saveStore(Store store) {
        return storeRep.save(store);
    }

    public Job_Application saveApplication(Job_Application application) {
        return jobRep.save(application);
    }
}
