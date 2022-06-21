package com.tqs.trackit.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.tqs.trackit.model.JobApplication;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
public class JobApplicationRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JobApplicationRepository jobRepository;

    @Test
    void whenFindJob_ApplicationByExistingId_thenReturnJob_Application() {
        JobApplication job_app = new JobApplication("Paulo","Silva",LocalDate.of(1984, 2, 3),"943526152","paulo.silva@ua.pt","link_to_photo","link_to_cv");
        entityManager.persistAndFlush(job_app);

        JobApplication fromDb = jobRepository.findById(job_app.getId()).orElse(null);
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getFirstName()).isEqualTo(job_app.getFirstName());
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        JobApplication fromDb = jobRepository.findById(-111L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void whenDelete1JobApplicationById_thenReturnListOfJobApplications() {
        Pageable elements = PageRequest.of(0, 6);
        JobApplication job_app = new JobApplication("Paulo","Silva",LocalDate.of(1984, 2, 3),"943526152","paulo.silva@ua.pt","link_to_photo","link_to_cv");
        entityManager.persistAndFlush(job_app);

        jobRepository.deleteById(job_app.getId());

        Page<JobApplication> pageFromDb = jobRepository.findAll(elements);
        List<JobApplication> fromDb = pageFromDb.getContent();
        assertThat(fromDb.size()).isEqualTo(0);
    }
    
}


