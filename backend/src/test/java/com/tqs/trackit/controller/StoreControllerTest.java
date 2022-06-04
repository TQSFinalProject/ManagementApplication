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
import com.tqs.trackit.model.Order;
import com.tqs.trackit.model.Store;
import com.tqs.trackit.repository.OrderRepository;
import com.tqs.trackit.repository.StoreRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = TrackitApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class StoreControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private StoreRepository storeRepository;

    @AfterEach
    public void resetDb() {
        storeRepository.deleteAll();
    }

    @Test
     void whenValidInput_thenCreateOrder() throws IOException, Exception {
        Store store1 = new Store("Store X",2.5,"Avenue X");
        
        mvc.perform(post("/api/stores").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(store1)));

        List<Store> found = storeRepository.findAll();
        assertThat(found).extracting(Store::getStoreName).containsOnly("Store X");
    }

    @Test
     void givenStores_whenGetStores_thenStatus200() throws Exception {
        Store store1 = new Store("Store X",2.5,"Avenue X");
        Store store2 = new Store("Store Y",3.0,"Avenue Y");
        Store store3 = new Store("Store Z",4.5,"Avenue Z");
        storeRepository.saveAndFlush(store1);
        storeRepository.saveAndFlush(store2);
        storeRepository.saveAndFlush(store3);

        mvc.perform(get("/api/stores").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(equalTo(3))))
                .andExpect(jsonPath("$[0].storeName", is("Store X")))
                .andExpect(jsonPath("$[1].storeName", is("Store Y")))
                .andExpect(jsonPath("$[2].storeName", is("Store Z")));
    }


    
}
