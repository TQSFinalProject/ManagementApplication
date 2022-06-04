package com.tqs.trackit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tqs.trackit.model.Rider;
import com.tqs.trackit.repository.RiderRepository;

@Service
public class RidersService {
    
    @Autowired
    RiderRepository riderRep;

    public List<Rider> getRiders() {
        return riderRep.findAll();
    }

    public Rider getRiderById(Long id) {
        return riderRep.findById(id).orElse(null);
    }

    public Rider saveRider(Rider rider) {
        return riderRep.save(rider);
    }
    
}
