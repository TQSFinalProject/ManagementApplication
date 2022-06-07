package com.tqs.trackit.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

    public List<Rider> getRidersByRating() {
        List<Rider> allRiders = riderRep.findAll();
        List<Rider> orderedRiders = new ArrayList<>();
        Map<Rider,Double> ridersByRating = new HashMap<>();
        
        for(Rider r : allRiders) {
            ridersByRating.put(r, r.getRatingMean());
        }

        List<Entry<Rider,Double>> list = new ArrayList<>(ridersByRating.entrySet());
        list.sort(Entry.comparingByValue());
        
        for(Entry<Rider,Double> e : list) {
            orderedRiders.add(e.getKey());
        }

        return orderedRiders;

    }

    public List<Rider> getRidersAlphabetically() {
        List<Rider> allRiders = riderRep.findAll();
        allRiders.sort(Comparator.comparing(Rider::getFirstName));
        return allRiders;
    }
    
}
