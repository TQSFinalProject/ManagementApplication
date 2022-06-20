package com.tqs.trackit.repository;

import com.tqs.trackit.model.Store;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
    
@Repository
public interface StoreRepository extends JpaRepository<Store,Long>{
    Page<Store> findAll(Pageable page); 
    Store findByStoreName(String name);
    Store findByUser_Username(String username);
}
