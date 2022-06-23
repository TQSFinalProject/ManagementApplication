package com.tqs.trackit.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tqs.trackit.model.Store;
import com.tqs.trackit.repository.StoreRepository;

@Service
public class StoresService {
    
    @Autowired
    StoreRepository storeRep;

    public Page<Store> getStores(Integer page) {
        Pageable elements = PageRequest.of(page, 6);
        return storeRep.findAll(elements);
    }

    public Store getStoreById(Long id) {
        return storeRep.findById(id).orElse(null);
    }

    public Page<Store> getStoreByName(Integer page, String name) {
        Pageable elements = PageRequest.of(page, 6);
        return storeRep.findByStoreName(name, elements);
    }

    public Page<Store> getStoreByAddress(Integer page, String address) {
        Pageable elements = PageRequest.of(page, 6);
        return storeRep.findByStoreAddress(address, elements);
    }

    public Store saveStore(Store store) {
        return storeRep.save(store);
    }

    public void deleteStore(Long storeId) {
        storeRep.deleteById(storeId);
    }
    
}
