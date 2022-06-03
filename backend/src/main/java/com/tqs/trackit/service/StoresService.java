package com.tqs.trackit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tqs.trackit.model.Store;
import com.tqs.trackit.repository.StoreRepository;

@Service
public class StoresService {
    
    @Autowired
    StoreRepository storeRep;

    public List<Store> getStores() {
        return storeRep.findAll();
    }

    public Store getStoreById(Long id) {
        return storeRep.findById(id).orElse(null);
    }

    public Store saveStore(Store store) {
        return storeRep.save(store);
    }
    
}
