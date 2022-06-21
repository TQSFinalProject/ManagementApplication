package com.tqs.trackit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tqs.trackit.dtos.LocationDTO;
import com.tqs.trackit.model.Rider;
import com.tqs.trackit.repository.RiderRepository;

@Service
public class RidersService {
    
    @Autowired
    RiderRepository riderRep;

    public Page<Rider> getRiders(Integer page) {
        Pageable elements = PageRequest.of(page, 6);
        return riderRep.findAll(elements);
    }

    public Rider getRiderById(Long id) {
        return riderRep.findById(id).orElse(null);
    }

    public Rider saveRider(Rider rider) {
        return riderRep.save(rider);
    }

    public Page<Rider> getRidersByNameAtoZ(Integer page) {
        Pageable elements = PageRequest.of(page, 6,Sort.by("firstName"));
        return riderRep.findAll(elements);
    }

    public Page<Rider> getRidersByNameZtoA(Integer page) {
        Pageable elements = PageRequest.of(page, 6,Sort.by("firstName").descending());
        return riderRep.findAll(elements);
    }

    public Page<Rider> getRidersByRating0to5(Integer page) {
        Pageable elements = PageRequest.of(page, 6,Sort.by("ratingMean"));
        return riderRep.findAll(elements);
    }

    public Page<Rider> getRidersByRating5to0(Integer page) {
        Pageable elements = PageRequest.of(page, 6,Sort.by("ratingMean").descending());
        return riderRep.findAll(elements);
    } 

    public void deleteRider(Long riderId) {
        riderRep.deleteById(riderId);
    }

    public LocationDTO updateRiderLocation(Rider rider, LocationDTO location) {
        rider.setLatitude(location.getLatitude());
        rider.setLongitude(location.getLongitude());
        riderRep.save(rider);
        return location;
    }
}
