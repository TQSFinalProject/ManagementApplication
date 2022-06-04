package com.tqs.trackit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tqs.trackit.model.JobApplication;
import com.tqs.trackit.repository.JobApplicationRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class JobApplicationsServiceTest {

    @Mock(lenient = true)
    private JobApplicationRepository jobRepository;

    @InjectMocks
    private JobApplicationsService jobService;

    @BeforeEach
    public void setUp() {
        JobApplication jobApp1 = new JobApplication("Paulo","Silva",LocalDate.of(1984, 2, 3),"943526152","paulo.silva@ua.pt","link_to_photo","link_to_cv");
        JobApplication jobApp2 = new JobApplication("Miguel","Marques",LocalDate.of(1999, 4, 21),"943583746","miguelm@ua.pt","link_to_photo","link_to_cv");

        jobApp1.setId(10L);

        List<JobApplication> allJobs = Arrays.asList(jobApp1, jobApp2);

        Mockito.when(jobRepository.findById(jobApp1.getId())).thenReturn(Optional.of(jobApp1));
        Mockito.when(jobRepository.findById(-1L)).thenReturn(Optional.empty());
        Mockito.when(jobRepository.findAll()).thenReturn(allJobs);
    }

    @Test
    void whenValidId_thenJobApplicationShouldBeFound() {
        JobApplication fromDb = jobService.getApplicationById(10L);
        assertThat(fromDb.getFirstName()).isEqualTo("Paulo");
        verifyFindByIdIsCalledOnce();
    }

    @Test
    void whenInvalidId_thenStoreShouldNotBeFound() {
        JobApplication fromDb = jobService.getApplicationById(-1L);
        verifyFindByIdIsCalledOnce();
        assertThat(fromDb).isNull();
    }

    @Test
    void given2JobApplications_whenGetAllJobApplications_thenReturn2JobApplications() {
        JobApplication jobApp1 = new JobApplication("Paulo","Silva",LocalDate.of(1984, 2, 3),"943526152","paulo.silva@ua.pt","link_to_photo","link_to_cv");
        JobApplication jobApp2 = new JobApplication("Miguel","Marques",LocalDate.of(1999, 4, 21),"943583746","miguelm@ua.pt","link_to_photo","link_to_cv");

        List<JobApplication> allApplications = jobService.getApplications();
        verifyFindJobApplicationsIsCalledOnce();
        assertThat(allApplications).hasSize(2).extracting(JobApplication::getFirstName).contains(jobApp1.getFirstName(),jobApp2.getFirstName());

    }


    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(jobRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
    }

    private void verifyFindJobApplicationsIsCalledOnce() {
        Mockito.verify(jobRepository, VerificationModeFactory.times(1)).findAll();
    }
    
}