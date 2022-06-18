package com.tqs.trackit.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tqs.trackit.model.JobApplication;
import com.tqs.trackit.repository.JobApplicationRepository;

@Service
public class JobApplicationsService {
    
    @Autowired
    JobApplicationRepository jobRep;

    public Page<JobApplication> getApplications(Integer page) {
        Pageable elements = PageRequest.of(page, 4);
        return jobRep.findAll(elements);
    }

    public JobApplication getApplicationById(Long id) {
        return jobRep.findById(id).orElse(null);
    }

    public JobApplication saveApplication(JobApplication application) {
        return jobRep.save(application);
    }
    
}
