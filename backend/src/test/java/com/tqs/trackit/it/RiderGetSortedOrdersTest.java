package com.tqs.trackit.it;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.tqs.trackit.JsonUtils;
import com.tqs.trackit.TrackitApplication;
import com.tqs.trackit.dtos.LocationDTO;
import com.tqs.trackit.dtos.LogInRequestDTO;
import com.tqs.trackit.dtos.RiderCreationDTO;
import com.tqs.trackit.dtos.StoreDTO;
import com.tqs.trackit.model.Order;
import com.tqs.trackit.model.Rider;
import com.tqs.trackit.repository.OrderRepository;
import com.tqs.trackit.repository.RiderRepository;
import com.tqs.trackit.repository.StoreRepository;
import com.tqs.trackit.service.AuthService;
import com.tqs.trackit.service.OrdersService;
import com.tqs.trackit.service.RidersService;
import com.tqs.trackit.service.StoresService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;  
import org.json.JSONObject; 

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = TrackitApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class RiderGetSortedOrdersTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StoreRepository storeRep;

    @Autowired
    private AuthService authServ;

    @Autowired
    private RidersService riderServ;

    @Autowired 
    private OrdersService orderServ;

    @AfterEach
    public void resetDb() {
        orderRepository.deleteAll();
        riderRepository.deleteAll();
        storeRep.deleteAll();
    }

    Rider rider1;
    String token;

    Order o1;
    Order o2;
    Order o3;
    Order o4;
    Order o5;
    Order o6;
    Order o7;
    Order o8;
    Order o9;
    Order o10;
    Order o11;
    Order o12;
    Order o13;

    @BeforeEach
    public void setUp() throws IOException, Exception {
        RiderCreationDTO rider = new RiderCreationDTO("Bob", "Wolf", "1234", "bobwolf", "1234", "");
        authServ.saveRider(rider.toRiderEntity());

        List<Rider> found = riderRepository.findAll();
        rider1 = found.get(0);

        LocationDTO location = new LocationDTO(10.0, 10.0);
        riderServ.updateRiderLocation(rider1, location);

        LogInRequestDTO req = new LogInRequestDTO("bobwolf", "1234");
        MvcResult result1 = mvc.perform(post("/authentication").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(req))).andReturn();
        JSONObject tokenJSON1 = new JSONObject(result1.getResponse().getContentAsString());
        token = tokenJSON1.getString("token");

        // o3 -> o2 -> o1 -> o4 -> o7 -> o12 -> o5 -> o6 -> o9 -> o8 -> o10 -> o11 -> o13
        // 31km
        o1 = new Order("created", "A", 10.2, 10.2, null, LocalDateTime.now(), null, null, null, null, "1234", null);
        // 25km
        o2 = new Order("created", "A", 10.2, 10.1, null, LocalDateTime.now(), null, null, null, null, "1234", null);
        // 11km
        o3 = new Order("created", "A", 10.1, 10.0, null, LocalDateTime.now(), null, null, null, null, "1234", null);
        // 33km
        o4 = new Order("created", "A", 10.3, 10.0, null, LocalDateTime.now(), null, null, null, null, "1234", null);
        // 46km
        o5 = new Order("created", "A", 10.4, 10.1, null, LocalDateTime.now(), null, null, null, null, "1234", null);
        // 56km
        o6 = new Order("created", "A", 10.1, 10.5, null, LocalDateTime.now(), null, null, null, null, "1234", null);
        // 40km
        o7 = new Order("created", "A", 10.3, 10.2, null, LocalDateTime.now(), null, null, null, null, "1234", null);
        // 71km
        o8 = new Order("created", "A", 10.5, 10.4, null, LocalDateTime.now(), null, null, null, null, "1234", null);
        // 59km
        o9 = new Order("created", "A", 10.2, 10.5, null, LocalDateTime.now(), null, null, null, null, "1234", null);
        
        orderServ.saveOrder(o1);
        orderServ.saveOrder(o2);
        orderServ.saveOrder(o3);
        orderServ.saveOrder(o4);
        orderServ.saveOrder(o5);
        orderServ.saveOrder(o6);
        orderServ.saveOrder(o7);
        orderServ.saveOrder(o8);
        orderServ.saveOrder(o9);
    }

    @Test
    void whenRiderGetOrders_thenClosestOrders() throws Exception {
        // o3 -> o2 -> o1 -> o4 -> o7 -> o5 -> o6 -> o9 -> o8
        mvc.perform(get("/api/rider/orders").header("Authorization", "Bearer "+token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].order.id", is((int)o3.getId())))
        .andExpect(jsonPath("$[1].order.id", is((int)o2.getId())))
        .andExpect(jsonPath("$[2].order.id", is((int)o1.getId())))
        .andExpect(jsonPath("$[3].order.id", is((int)o4.getId())))
        .andExpect(jsonPath("$[4].order.id", is((int)o7.getId())))
        .andExpect(jsonPath("$[5].order.id", is((int)o5.getId())))
        .andExpect(jsonPath("$[6].order.id", is((int)o6.getId())))
        .andExpect(jsonPath("$[7].order.id", is((int)o9.getId())))
        .andExpect(jsonPath("$[8].order.id", is((int)o8.getId())));
    }

    @Test
    void whenRiderGetOrdersWithLimit_thenXClosestOrders() throws Exception {
        // o3 -> o2 -> o1 -> o4 -> o7 -> o5 -> o6 -> o9 -> o8
        mvc.perform(get("/api/rider/orders?limit=5").header("Authorization", "Bearer "+token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(5)))
        .andExpect(jsonPath("$[0].order.id", is((int)o3.getId())))
        .andExpect(jsonPath("$[1].order.id", is((int)o2.getId())))
        .andExpect(jsonPath("$[2].order.id", is((int)o1.getId())))
        .andExpect(jsonPath("$[3].order.id", is((int)o4.getId())))
        .andExpect(jsonPath("$[4].order.id", is((int)o7.getId())));

        mvc.perform(get("/api/rider/orders?limit=100").header("Authorization", "Bearer "+token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(9)))
        .andExpect(jsonPath("$[0].order.id", is((int)o3.getId())))
        .andExpect(jsonPath("$[1].order.id", is((int)o2.getId())))
        .andExpect(jsonPath("$[2].order.id", is((int)o1.getId())))
        .andExpect(jsonPath("$[3].order.id", is((int)o4.getId())))
        .andExpect(jsonPath("$[4].order.id", is((int)o7.getId())))
        .andExpect(jsonPath("$[5].order.id", is((int)o5.getId())))
        .andExpect(jsonPath("$[6].order.id", is((int)o6.getId())))
        .andExpect(jsonPath("$[7].order.id", is((int)o9.getId())))
        .andExpect(jsonPath("$[8].order.id", is((int)o8.getId())));
    }

    @Test
    void ifNotRider_thenUnauthorized() throws Exception {
        StoreDTO store = new StoreDTO("CDV", 1.99, "UA", 10.0, 10.0, "CDV", "CDV123", 0L);

        mvc.perform(post("/registration/store").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(store)))
        .andExpect(status().isOk());

        LogInRequestDTO req = new LogInRequestDTO("CDV", "CDV123");
        MvcResult result1 = mvc.perform(post("/authentication").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(req))).andReturn();
        JSONObject tokenJSON1 = new JSONObject(result1.getResponse().getContentAsString());
        String storeToken = tokenJSON1.getString("token");

        mvc.perform(get("/api/rider/orders?limit=5").header("Authorization", "Bearer "+storeToken))
        .andExpect(status().isUnauthorized());

        LocationDTO location = new LocationDTO(10.5, 10.9);
        mvc.perform(post("/api/rider/updateLocation").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(location)).header("Authorization", "Bearer "+storeToken))
        .andExpect(status().isUnauthorized());
    }

    @Test
    void whenUpdateLocation_thenNewLocation() throws IOException, Exception {
        assertEquals(10.0, rider1.getLatitude());
        assertEquals(10.0, rider1.getLongitude());

        LocationDTO location = new LocationDTO(10.5, 10.9);
        mvc.perform(post("/api/rider/updateLocation").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(location)).header("Authorization", "Bearer "+token))
        .andExpect(status().isOk());

        rider1 = riderRepository.findById(rider1.getId()).get();

        assertEquals(10.9, rider1.getLatitude());
        assertEquals(10.5, rider1.getLongitude());
    }

    
}
