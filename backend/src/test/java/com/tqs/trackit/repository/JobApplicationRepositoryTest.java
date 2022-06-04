package com.tqs.trackit.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.tqs.trackit.model.JobApplication;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

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
    
}


