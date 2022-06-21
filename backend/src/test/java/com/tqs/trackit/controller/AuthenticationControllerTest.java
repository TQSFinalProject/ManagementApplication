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
import com.tqs.trackit.dtos.LogInRequestDTO;
import com.tqs.trackit.dtos.RiderCreationDTO;
import com.tqs.trackit.dtos.StoreDTO;
import com.tqs.trackit.model.Rider;
import com.tqs.trackit.model.Store;
import com.tqs.trackit.repository.RiderRepository;
import com.tqs.trackit.repository.StoreRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.util.List;  

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = TrackitApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private RiderRepository riderRepository;

    @AfterEach
    public void resetDb() {
        storeRepository.deleteAll();
        riderRepository.deleteAll();
    }
    
    @Test
    void whenRegisterRider_thenSuccessfullAuth() throws IOException, Exception {
        RiderCreationDTO rider = new RiderCreationDTO("Bob", "Wolf", "1234", "bobwolf", "1234", "");

        mvc.perform(post("/registration/rider").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(rider)))
        .andExpect(status().isOk());

        // User was saved
        List<Rider> found = riderRepository.findAll();
        assertThat(found).extracting(Rider::getFirstName).containsOnly("Bob");
        Rider riderFound = found.get(0);

        // Password was not stored in plaintext
        assertNotEquals(rider.getPassword(), riderFound.getUser().getPassword());

        // User can successfully authenticate, recieving a token
        LogInRequestDTO credentials = new LogInRequestDTO(rider.getUsername(), rider.getPassword());
        mvc.perform(post("/authentication").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(credentials)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").exists())
        .andReturn();
    }

    @Test
    void whenRegisterStore_thenSuccessfullAuth() throws IOException, Exception {
        StoreDTO store = new StoreDTO("CDV", 1.99, "UA", 10.0, 10.0, "CDV", "CDV123", 0L);

        mvc.perform(post("/registration/store").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(store)))
        .andExpect(status().isOk());

        // User was saved
        List<Store> found = storeRepository.findAll();
        assertThat(found).extracting(Store::getStoreName).containsOnly("CDV");

        // Password was not stored in plaintext
        assertNotEquals(store.getPassword(), found.get(0).getUser().getPassword());

        // User can successfully authenticate, recieving a token
        LogInRequestDTO credentials = new LogInRequestDTO(store.getUsername(), store.getPassword());
        mvc.perform(post("/authentication").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(credentials)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").exists())
        .andReturn();
    }


    @Test
    void whenAuthenticateWithNoCreds_thenUnsuccessfulAuth() throws IOException, Exception {
        LogInRequestDTO unregisteredCredentials = new LogInRequestDTO("not", "registered");

        mvc.perform(post("/authentication").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(unregisteredCredentials)))
        .andExpect(status().is(500))
        .andExpect(jsonPath("$.message", is("Bad credentials")));
    }

    @Test
    void whenStoreRegisterWithDuplicatedUsername_thenUnsuccessfulRegister() throws IOException, Exception {
        StoreDTO store1 = new StoreDTO("CDV", 1.99, "UA", 10.0, 10.0, "CDV", "CDV123", 0L);
        StoreDTO store2 = new StoreDTO("CDV2", 1.99, "UA", 10.0, 10.0, "CDV", "CDV123", 1L);

        mvc.perform(post("/registration/store").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(store1)))
        .andExpect(status().isOk());

        mvc.perform(post("/registration/store").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(store2)))
        .andExpect(status().is(409));

    }

    @Test
    void whenRiderRegisterWithDuplicatedUsername_thenUnsuccessfulRegister() throws IOException, Exception {
        RiderCreationDTO rider1 = new RiderCreationDTO("Bob", "Wolf", "1234", "bobwolf", "1234", "");
        RiderCreationDTO rider2 = new RiderCreationDTO("Bob", "Wolf2", "1234", "bobwolf", "1234", "");

        mvc.perform(post("/registration/rider").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(rider1)))
        .andExpect(status().isOk());

        mvc.perform(post("/registration/rider").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(rider2)))
        .andExpect(status().is(409));

    }

}
