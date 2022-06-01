package com.tqs.trackit.repository;

import com.tqs.trackit.model.Job_Application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Job_ApplicationRepository extends JpaRepository<Job_Application,Long>{
    
}
