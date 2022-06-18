package com.tqs.trackit.repository;

import com.tqs.trackit.model.JobApplication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication,Long>{
    Page<JobApplication> findAll(Pageable page);
}
