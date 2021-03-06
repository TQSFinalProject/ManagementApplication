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
import com.tqs.trackit.dtos.StoreDTO;
import com.tqs.trackit.model.Store;
import com.tqs.trackit.repository.StoreRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
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
        StoreDTO store1 = new StoreDTO("Store X",2.5,"Avenue X", 10.0, 10.0,"X","X123",1L);

        mvc.perform(post("/registration/store").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(store1)));

        List<Store> found = storeRepository.findAll();
        assertThat(found).extracting(Store::getStoreName).containsOnly("Store X");
    }

    @Test
     void givenStores_whenGetStores_thenStatus200FromPage0() throws Exception {
        Store store1 = new Store("Store X",2.5,"Avenue X", 10.0, 10.0,"X","X");
        Store store2 = new Store("Store Y",3.0,"Avenue Y", 10.0, 10.0,"Y","X");
        Store store3 = new Store("Store Z",4.5,"Avenue Z", 10.0, 10.0,"Z","X");
        storeRepository.saveAndFlush(store1);
        storeRepository.saveAndFlush(store2);
        storeRepository.saveAndFlush(store3);

        mvc.perform(get("/api/stores?page=0").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(equalTo(3))))
                .andExpect(jsonPath("$.content[0].storeName", is(store1.getStoreName())))
                .andExpect(jsonPath("$.content[1].storeName", is(store2.getStoreName())))
                .andExpect(jsonPath("$.content[2].storeName", is(store3.getStoreName())));
    }

    @Test
     void givenStores_whenGetStores_thenStatus200FromPage1() throws Exception {
        Store store1 = new Store("Store X",2.5,"Avenue X", 10.0, 10.0,"X","X");
        Store store2 = new Store("Store Y",3.0,"Avenue Y", 10.0, 10.0,"Y","X");
        Store store3 = new Store("Store Z",4.5,"Avenue Z", 10.0, 10.0,"Z","X");
        storeRepository.saveAndFlush(store1);
        storeRepository.saveAndFlush(store2);
        storeRepository.saveAndFlush(store3);

        mvc.perform(get("/api/stores?page=1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(equalTo(0))));
    }

    @Test
    void givenStoreId_whenGetStoreById_thenStatus200() throws Exception {
        Store store1 = new Store("Store X",2.5,"Avenue X", 10.0, 10.0,"X","X");
        storeRepository.saveAndFlush(store1);

        mvc.perform(get("/api/stores/{storeId}",store1.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", equalTo(7)))
                .andExpect(jsonPath("$.storeName", is(store1.getStoreName())));
    }

    @Test
    void whenDeleteStoreById_thenStatus200() throws Exception {
        Store store1 = new Store("Store X",2.5,"Avenue X",10.0,10.0,"X","X");
        storeRepository.saveAndFlush(store1);

        mvc.perform(delete("/api/stores/{storeId}",store1.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Deleted")));
    }

    @Test
    void whenDeleteStoreByMalformedId_thenStatus400() throws Exception {
        Store store1 = new Store("Store X",2.5,"Avenue X",10.0,10.0,"X","X");
        storeRepository.saveAndFlush(store1);

        mvc.perform(delete("/api/stores/{storeId}","NotAnId").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Not a Valid ID")));
    }

    @Test
    void whenDeleteStoreByNonExistentId_thenStatus500() throws Exception {
        Store store1 = new Store("Store X",2.5,"Avenue X",10.0,10.0,"X","X");
        storeRepository.saveAndFlush(store1);

        mvc.perform(delete("/api/stores/{storeId}","5").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    void givenStoreName_whenGetStoreByName_thenStatus200() throws Exception {
        Store store1 = new Store("Store X",2.5,"Avenue X", 10.0, 10.0,"X","X");
        storeRepository.saveAndFlush(store1);

        mvc.perform(get("/api/stores/name/{storeName}",store1.getStoreName()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].length()", equalTo(7)))
                .andExpect(jsonPath("$.content[0].storeName", is(store1.getStoreName())));
    }

    @Test
    void givenStoreAddress_whenGetStoreByAddress_thenStatus200() throws Exception {
        Store store1 = new Store("Store X",2.5,"Avenue X", 10.0, 10.0,"X","X");
        storeRepository.saveAndFlush(store1);

        mvc.perform(get("/api/stores/address/{storeAddress}",store1.getStoreAddress()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].length()", equalTo(7)))
                .andExpect(jsonPath("$.content[0].storeAddress", is(store1.getStoreAddress())));
    }
    
}
