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
import com.tqs.trackit.model.Rider;
import com.tqs.trackit.repository.RiderRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = TrackitApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class RiderControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RiderRepository riderRepository;

    @AfterEach
    public void resetDb() {
        riderRepository.deleteAll();
    }

    @Test
     void whenValidInput_thenCreateRider() throws IOException, Exception {
        List<Double> ratings = new ArrayList<>();
        ratings.add(4.5);
        ratings.add(4.0);
        Rider rider1 = new Rider("Miguel","Ferreira","937485748","miguelf","password","link",49.4578,76.93284,ratings);
        
        mvc.perform(post("/api/riders").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(rider1)));

        List<Rider> found = riderRepository.findAll();
        assertThat(found).extracting(Rider::getFirstName).containsOnly("Miguel");
    }

    @Test
     void givenRiders_whenGetRiders_thenStatus200() throws Exception {
        List<Double> ratings = new ArrayList<>();
        ratings.add(4.5);
        ratings.add(4.0);
        Rider rider1 = new Rider("Miguel","Ferreira","937485748","miguelf","password","link",49.4578,76.93284,ratings);
        Rider rider2 = new Rider("Afonso","Campos","937451448","afonsoc","password","link",49.4455,32.93284,ratings);
        Rider rider3 = new Rider("Ana","Monteiro","9153726384","anam","password","link",39.4455,12.93284,ratings);

        riderRepository.saveAndFlush(rider1);
        riderRepository.saveAndFlush(rider2);
        riderRepository.saveAndFlush(rider3);

        mvc.perform(get("/api/riders").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(equalTo(3))))
                .andExpect(jsonPath("$[0].firstName", is("Miguel")))
                .andExpect(jsonPath("$[1].firstName", is("Afonso")))
                .andExpect(jsonPath("$[2].firstName", is("Ana")));
    }

    @Test
    void givenRiderId_whenGetRiderById_thenStatus200() throws Exception {
        List<Double> ratings = new ArrayList<>();
        ratings.add(4.5);
        ratings.add(4.0);
        Rider rider1 = new Rider("Miguel","Ferreira","937485748","miguelf","password","link",49.4578,76.93284,ratings);
        
        riderRepository.saveAndFlush(rider1);

        mvc.perform(get("/api/riders/{riderId}",rider1.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", equalTo(10)))
                .andExpect(jsonPath("$.firstName", is("Miguel")));
    }

    @Test
    void givenRiders_whenGetRidersOrderedAlphabetically_thenStatus200() throws Exception {
        List<Double> ratings1 = new ArrayList<>();
        ratings1.add(4.5);
        ratings1.add(4.0);
        List<Double> ratings2 = new ArrayList<>();
        ratings2.add(2.5);
        ratings2.add(3.5);
        List<Double> ratings3 = new ArrayList<>();
        ratings3.add(5.0);
        ratings3.add(3.0);
        Rider rider1 = new Rider("Miguel","Ferreira","937485748","miguelf","password","link",49.4578,76.93284,ratings1);
        Rider rider2 = new Rider("Afonso","Campos","937451448","afonsoc","password","link",49.4455,32.93284,ratings2);
        Rider rider3 = new Rider("Ana","Monteiro","9153726384","anam","password","link",39.4455,12.93284,ratings3);

        riderRepository.saveAndFlush(rider1);
        riderRepository.saveAndFlush(rider2);
        riderRepository.saveAndFlush(rider3);

        mvc.perform(get("/api/riders/sortByName").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(equalTo(3))))
        .andExpect(jsonPath("$[0].firstName", is("Afonso")))
        .andExpect(jsonPath("$[1].firstName", is("Ana")))
        .andExpect(jsonPath("$[2].firstName", is("Miguel")));

    }

    @Test
    void givenRiders_whenGetRidersOrderedByRating_thenStatus200() throws Exception {
        List<Double> ratings1 = new ArrayList<>();
        ratings1.add(4.5);
        ratings1.add(4.0);
        List<Double> ratings2 = new ArrayList<>();
        ratings2.add(2.5);
        ratings2.add(3.5);
        List<Double> ratings3 = new ArrayList<>();
        ratings3.add(5.0);
        ratings3.add(3.0);
        Rider rider1 = new Rider("Miguel","Ferreira","937485748","miguelf","password","link",49.4578,76.93284,ratings1);
        Rider rider2 = new Rider("Afonso","Campos","937451448","afonsoc","password","link",49.4455,32.93284,ratings2);
        Rider rider3 = new Rider("Ana","Monteiro","9153726384","anam","password","link",39.4455,12.93284,ratings3);

        riderRepository.saveAndFlush(rider1);
        riderRepository.saveAndFlush(rider2);
        riderRepository.saveAndFlush(rider3);

        mvc.perform(get("/api/riders/sortByRating").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(equalTo(3))))
        .andExpect(jsonPath("$[0].firstName", is("Afonso")))
        .andExpect(jsonPath("$[1].firstName", is("Ana")))
        .andExpect(jsonPath("$[2].firstName", is("Miguel")));

    }


    
}
