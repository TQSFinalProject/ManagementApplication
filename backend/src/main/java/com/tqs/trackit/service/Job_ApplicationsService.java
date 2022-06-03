package com.tqs.trackit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tqs.trackit.model.Job_Application;
import com.tqs.trackit.repository.Job_ApplicationRepository;

@Service
public class Job_ApplicationsService {
    
    @Autowired
    Job_ApplicationRepository jobRep;

    public List<Job_Application> getApplications() {
        return jobRep.findAll();
    }

    public Job_Application getApplicationById(Long id) {
        return jobRep.findById(id).orElse(null);
    }

    public Job_Application saveApplication(Job_Application application) {
        return jobRep.save(application);
    }
    
}
