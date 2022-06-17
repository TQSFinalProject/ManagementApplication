package com.tqs.trackit.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.tqs.trackit.JsonUtils;
import com.tqs.trackit.TrackitApplication;
import com.tqs.trackit.model.JobApplication;
import com.tqs.trackit.repository.JobApplicationRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = TrackitApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class JobApplicationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JobApplicationRepository jobRepository;

    @AfterEach
    public void resetDb() {
        jobRepository.deleteAll();
    }

    @Test
     void whenValidInput_thenCreateJobApplication() throws IOException, Exception {
        JobApplication jobApp1 = new JobApplication("Paulo","Silva",LocalDate.of(1984, 2, 3),"943526152","paulo.silva@ua.pt","link_to_photo","link_to_cv");

        mvc.perform(post("/api/jobApplications").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(jobApp1)));

        List<JobApplication> found = jobRepository.findAll();
        assertThat(found).extracting(JobApplication::getFirstName).containsOnly("Paulo");
    }

    @Test
     void givenJobApplications_whenGetJobApplicationsFromPage0_thenStatus200() throws Exception {
        JobApplication jobApp1 = new JobApplication("Paulo","Silva",LocalDate.of(1984, 2, 3),"943526152","paulo.silva@ua.pt","link_to_photo","link_to_cv");
        JobApplication jobApp2 = new JobApplication("Miguel","Marques",LocalDate.of(1999, 4, 21),"943583746","miguelm@ua.pt","link_to_photo","link_to_cv");
        jobRepository.saveAndFlush(jobApp1);
        jobRepository.saveAndFlush(jobApp2);

        mvc.perform(get("/api/jobApplications?page=0").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(equalTo(2))))
                .andExpect(jsonPath("$.content[0].firstName", is("Paulo")))
                .andExpect(jsonPath("$.content[1].firstName", is("Miguel")));
    }

    @Test
     void givenJobApplications_whenGetJobApplicationsFromPage1_thenStatus200() throws Exception {
        JobApplication jobApp1 = new JobApplication("Paulo","Silva",LocalDate.of(1984, 2, 3),"943526152","paulo.silva@ua.pt","link_to_photo","link_to_cv");
        JobApplication jobApp2 = new JobApplication("Miguel","Marques",LocalDate.of(1999, 4, 21),"943583746","miguelm@ua.pt","link_to_photo","link_to_cv");
        jobRepository.saveAndFlush(jobApp1);
        jobRepository.saveAndFlush(jobApp2);

        mvc.perform(get("/api/jobApplications?page=1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(equalTo(0))));
    }

    @Test
    void givenJobApplicationId_whenGetJobApplicationById_thenStatus200() throws Exception {
        JobApplication jobApp1 = new JobApplication("Paulo","Silva",LocalDate.of(1984, 2, 3),"943526152","paulo.silva@ua.pt","link_to_photo","link_to_cv");
        jobRepository.saveAndFlush(jobApp1);

        mvc.perform(get("/api/jobApplications/{jobApplicationId}",jobApp1.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", equalTo(8)))
                .andExpect(jsonPath("$.firstName", is("Paulo")));
    }


    
}
