package com.tqs.trackit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    // public List<Rider> getRidersByRating() {
    //     List<Rider> allRiders = riderRep.findAll();
    //     List<Rider> orderedRiders = new ArrayList<>();
    //     Map<Rider,Double> ridersByRating = new HashMap<>();
        
    //     for(Rider r : allRiders) {
    //         ridersByRating.put(r, r.ratingMean());
    //     }

    //     List<Entry<Rider,Double>> list = new ArrayList<>(ridersByRating.entrySet());
    //     list.sort(Entry.comparingByValue());
        
    //     for(Entry<Rider,Double> e : list) {
    //         orderedRiders.add(e.getKey());
    //     }

    //     return orderedRiders;

    // }

    // public List<Rider> getRidersAlphabetically() {
    //     List<Rider> allRiders = riderRep.findAll();
    //     allRiders.sort(Comparator.comparing(Rider::getFirstName));
    //     return allRiders;
    // }
    
}
