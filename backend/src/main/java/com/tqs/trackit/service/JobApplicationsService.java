package com.tqs.trackit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tqs.trackit.model.JobApplication;
import com.tqs.trackit.repository.JobApplicationRepository;

@Service
public class JobApplicationsService {
    
    @Autowired
    JobApplicationRepository jobRep;

    public List<JobApplication> getApplications() {
        return jobRep.findAll();
    }

    public JobApplication getApplicationById(Long id) {
        return jobRep.findById(id).orElse(null);
    }

    public JobApplication saveApplication(JobApplication application) {
        return jobRep.save(application);
    }
    
}
