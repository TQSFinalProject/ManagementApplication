package com.tqs.trackit.it;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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
import com.tqs.trackit.dtos.LogInRequestDTO;
import com.tqs.trackit.dtos.OrderCreationDTO;
import com.tqs.trackit.dtos.RiderCreationDTO;
import com.tqs.trackit.model.Rider;
import com.tqs.trackit.model.Store;
import com.tqs.trackit.repository.OrderRepository;
import com.tqs.trackit.repository.RiderRepository;
import com.tqs.trackit.repository.StoreRepository;
import com.tqs.trackit.repository.UserRepository;
import com.tqs.trackit.service.AuthService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;
import java.time.LocalDateTime;

import org.json.JSONObject;  

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = TrackitApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderPipelineTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private AuthService authServ;

    @Autowired
    private RiderRepository riderRep;

    @Autowired
    private StoreRepository storeRep;

    @Autowired
    private UserRepository userRep;

    @Autowired
    private OrderRepository orderRep;


    @AfterAll
    public void resetDb() {
        riderRep.deleteAll();
        storeRep.deleteAll();
        userRep.deleteAll();
        orderRep.deleteAll();
    }

    String riderToken;
    String storeToken;

    String badRiderToken;
    String badStoreToken;

    Store store;
    Rider rider;
    Long orderId;

    @BeforeAll
    void setUp() throws IOException, Exception {
        store = new Store("CDV", 1.99, "UA", 10.0, 10.0, "CDV", "CDV123", 0L);
        store = authServ.saveStore(store);
        LogInRequestDTO req1 = new LogInRequestDTO("CDV", "CDV123");
        MvcResult result1 = mvc.perform(post("/authentication").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(req1))).andReturn();
        JSONObject tokenJSON1 = new JSONObject(result1.getResponse().getContentAsString());
        storeToken = tokenJSON1.getString("token");

        rider = new RiderCreationDTO("Bob", "Wolf", "1234", "bobwolf", "1234", "").toRiderEntity();
        rider = authServ.saveRider(rider);
        LogInRequestDTO req2 = new LogInRequestDTO("bobwolf", "1234");
        MvcResult result2 = mvc.perform(post("/authentication").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(req2))).andReturn();
        JSONObject tokenJSON2 = new JSONObject(result2.getResponse().getContentAsString());
        riderToken = tokenJSON2.getString("token");    

        Store store2 = new Store("CDV2", 1.99, "UA", 10.0, 10.0, "CDV2", "CDV123", 0L);
        store2 = authServ.saveStore(store2);
        LogInRequestDTO req3 = new LogInRequestDTO("CDV2", "CDV123");
        MvcResult result3 = mvc.perform(post("/authentication").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(req3))).andReturn();
        JSONObject tokenJSON3 = new JSONObject(result3.getResponse().getContentAsString());
        badStoreToken = tokenJSON3.getString("token");

        Rider rider2 = new RiderCreationDTO("Bob", "Wolf", "1234", "bobwolf2", "1234", "").toRiderEntity();
        rider2 = authServ.saveRider(rider2);
        LogInRequestDTO req4 = new LogInRequestDTO("bobwolf2", "1234");
        MvcResult result4 = mvc.perform(post("/authentication").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(req4))).andReturn();
        JSONObject tokenJSON4 = new JSONObject(result4.getResponse().getContentAsString());
        badRiderToken = tokenJSON4.getString("token");  
    }

    @Test
    @Order(1)
    void storeCreatesOrder() throws IOException, Exception {
        OrderCreationDTO orderDto = new OrderCreationDTO("requested", "UA", 10.0, 10.0, LocalDateTime.now(), "details", "1234");

        MvcResult orderResult = mvc.perform(post("/api/store/order").contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtils.toJson(orderDto)).header("Authorization", "Bearer "+storeToken))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.orderStatus", is("requested")))
        .andExpect(jsonPath("$.deliveryAddress", is("UA")))
        .andExpect(jsonPath("$.deliveryLat", is(10.0)))
        .andExpect(jsonPath("$.deliveryLong", is(10.0)))
        .andExpect(jsonPath("$.estimatedDeliveryTime").isEmpty())
        .andExpect(jsonPath("$.submitedTime").isNotEmpty())
        .andExpect(jsonPath("$.deliveryTime").isEmpty())
        .andExpect(jsonPath("$.riderId").isEmpty())
        .andExpect(jsonPath("$.storeId", is((int)store.getId())))
        .andExpect(jsonPath("$.orderDetails", is("details")))
        .andExpect(jsonPath("$.phone", is("1234")))
        .andExpect(jsonPath("$.rating").isEmpty())
        .andReturn();
        orderId = Long.parseLong(new JSONObject(orderResult.getResponse().getContentAsString()).getString("id"));
        
        mvc.perform(get("/api/store/order/"+orderId).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer "+storeToken))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.order.orderStatus", is("requested")))
        .andExpect(jsonPath("$.order.deliveryAddress", is("UA")))
        .andExpect(jsonPath("$.order.deliveryLat", is(10.0)))
        .andExpect(jsonPath("$.order.deliveryLong", is(10.0)))
        .andExpect(jsonPath("$.order.estimatedDeliveryTime").isEmpty())
        .andExpect(jsonPath("$.order.submitedTime").isNotEmpty())
        .andExpect(jsonPath("$.order.deliveryTime").isEmpty())
        .andExpect(jsonPath("$.order.riderId").isEmpty())
        .andExpect(jsonPath("$.order.storeId", is((int)store.getId())))
        .andExpect(jsonPath("$.order.orderDetails", is("details")))
        .andExpect(jsonPath("$.order.phone", is("1234")))
        .andExpect(jsonPath("$.order.rating").isEmpty())
        .andExpect(jsonPath("$.rider").isEmpty());
    }

    @Test
    @Order(2)
    void unknownStoreTriesToAccessOrderInfo() throws IOException, Exception {
        mvc.perform(get("/api/store/order/"+orderId).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer "+badStoreToken))
        .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(3)
    void riderAcceptsOrder() throws IOException, Exception {
        MvcResult orderResult = mvc.perform(put("/api/rider/order/accept/"+orderId).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer "+riderToken))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.orderStatus", is("accepted")))
        .andExpect(jsonPath("$.deliveryAddress", is("UA")))
        .andExpect(jsonPath("$.deliveryLat", is(10.0)))
        .andExpect(jsonPath("$.deliveryLong", is(10.0)))
        .andExpect(jsonPath("$.estimatedDeliveryTime").isNotEmpty())
        .andExpect(jsonPath("$.submitedTime").isNotEmpty())
        .andExpect(jsonPath("$.deliveryTime").isEmpty())
        .andExpect(jsonPath("$.riderId", is((int)rider.getId())))
        .andExpect(jsonPath("$.storeId", is((int)store.getId())))
        .andExpect(jsonPath("$.orderDetails", is("details")))
        .andExpect(jsonPath("$.phone", is("1234")))
        .andExpect(jsonPath("$.rating").isEmpty())
        .andReturn();
        orderId = Long.parseLong(new JSONObject(orderResult.getResponse().getContentAsString()).getString("id"));
        
        mvc.perform(get("/api/store/order/"+orderId).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer "+storeToken))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.order.orderStatus", is("accepted")))
        .andExpect(jsonPath("$.order.deliveryAddress", is("UA")))
        .andExpect(jsonPath("$.order.deliveryLat", is(10.0)))
        .andExpect(jsonPath("$.order.deliveryLong", is(10.0)))
        .andExpect(jsonPath("$.order.estimatedDeliveryTime").isNotEmpty())
        .andExpect(jsonPath("$.order.submitedTime").isNotEmpty())
        .andExpect(jsonPath("$.order.deliveryTime").isEmpty())
        .andExpect(jsonPath("$.order.riderId", is((int)rider.getId())))
        .andExpect(jsonPath("$.order.storeId", is((int)store.getId())))
        .andExpect(jsonPath("$.order.orderDetails", is("details")))
        .andExpect(jsonPath("$.order.phone", is("1234")))
        .andExpect(jsonPath("$.order.rating").isEmpty())
        .andExpect(jsonPath("$.rider.id", is((int)rider.getId())))
        .andExpect(jsonPath("$.rider.ratingMean", is(0.0)))
        .andExpect(jsonPath("$.rider.phone", is("1234")));
    }

    @Test
    @Order(4)
    void riderWithActiveOrderCantAcceptMore() throws IOException, Exception {
        OrderCreationDTO orderDto = new OrderCreationDTO("requested", "UA", 10.0, 10.0, LocalDateTime.now(), "details", "1234");

        MvcResult orderResult = mvc.perform(post("/api/store/order").contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtils.toJson(orderDto)).header("Authorization", "Bearer "+storeToken))
        .andExpect(status().isOk())
        .andReturn();
        Long orderId2 = Long.parseLong(new JSONObject(orderResult.getResponse().getContentAsString()).getString("id"));

        mvc.perform(put("/api/rider/order/accept/"+orderId2).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer "+riderToken))
        .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(5)
    void unknownRiderTriesToDeliverOrder() throws IOException, Exception {
        mvc.perform(put("/api/rider/order/delivering/"+orderId).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer "+badRiderToken))
        .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(6)
    void riderStartsDeliveringOrder() throws IOException, Exception {
        MvcResult orderResult = mvc.perform(put("/api/rider/order/delivering/"+orderId).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer "+riderToken))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.orderStatus", is("delivering")))
        .andExpect(jsonPath("$.deliveryAddress", is("UA")))
        .andExpect(jsonPath("$.deliveryLat", is(10.0)))
        .andExpect(jsonPath("$.deliveryLong", is(10.0)))
        .andExpect(jsonPath("$.estimatedDeliveryTime").isNotEmpty())
        .andExpect(jsonPath("$.submitedTime").isNotEmpty())
        .andExpect(jsonPath("$.deliveryTime").isEmpty())
        .andExpect(jsonPath("$.riderId", is((int)rider.getId())))
        .andExpect(jsonPath("$.storeId", is((int)store.getId())))
        .andExpect(jsonPath("$.orderDetails", is("details")))
        .andExpect(jsonPath("$.phone", is("1234")))
        .andExpect(jsonPath("$.rating").isEmpty())
        .andReturn();
        orderId = Long.parseLong(new JSONObject(orderResult.getResponse().getContentAsString()).getString("id"));
        
        mvc.perform(get("/api/store/order/"+orderId).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer "+storeToken))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.order.orderStatus", is("delivering")))
        .andExpect(jsonPath("$.order.deliveryAddress", is("UA")))
        .andExpect(jsonPath("$.order.deliveryLat", is(10.0)))
        .andExpect(jsonPath("$.order.deliveryLong", is(10.0)))
        .andExpect(jsonPath("$.order.estimatedDeliveryTime").isNotEmpty())
        .andExpect(jsonPath("$.order.submitedTime").isNotEmpty())
        .andExpect(jsonPath("$.order.deliveryTime").isEmpty())
        .andExpect(jsonPath("$.order.riderId", is((int)rider.getId())))
        .andExpect(jsonPath("$.order.storeId", is((int)store.getId())))
        .andExpect(jsonPath("$.order.orderDetails", is("details")))
        .andExpect(jsonPath("$.order.phone", is("1234")))
        .andExpect(jsonPath("$.order.rating").isEmpty())
        .andExpect(jsonPath("$.rider.id", is((int)rider.getId())))
        .andExpect(jsonPath("$.rider.ratingMean", is(0.0)))
        .andExpect(jsonPath("$.rider.phone", is("1234")));
    }

    @Test
    @Order(7)
    void unknownRiderTriesToCompleteOrder() throws IOException, Exception {
        mvc.perform(put("/api/rider/order/complete/"+orderId).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer "+badRiderToken))
        .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(8)
    void riderCompletesOrder() throws IOException, Exception {
        MvcResult orderResult = mvc.perform(put("/api/rider/order/complete/"+orderId).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer "+riderToken))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.orderStatus", is("completed")))
        .andExpect(jsonPath("$.deliveryAddress", is("UA")))
        .andExpect(jsonPath("$.deliveryLat", is(10.0)))
        .andExpect(jsonPath("$.deliveryLong", is(10.0)))
        .andExpect(jsonPath("$.estimatedDeliveryTime").isNotEmpty())
        .andExpect(jsonPath("$.submitedTime").isNotEmpty())
        .andExpect(jsonPath("$.deliveryTime").isNotEmpty())
        .andExpect(jsonPath("$.riderId", is((int)rider.getId())))
        .andExpect(jsonPath("$.storeId", is((int)store.getId())))
        .andExpect(jsonPath("$.orderDetails", is("details")))
        .andExpect(jsonPath("$.phone", is("1234")))
        .andExpect(jsonPath("$.rating").isEmpty())
        .andReturn();
        orderId = Long.parseLong(new JSONObject(orderResult.getResponse().getContentAsString()).getString("id"));
        
        mvc.perform(get("/api/store/order/"+orderId).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer "+storeToken))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.order.orderStatus", is("completed")))
        .andExpect(jsonPath("$.order.deliveryAddress", is("UA")))
        .andExpect(jsonPath("$.order.deliveryLat", is(10.0)))
        .andExpect(jsonPath("$.order.deliveryLong", is(10.0)))
        .andExpect(jsonPath("$.order.estimatedDeliveryTime").isNotEmpty())
        .andExpect(jsonPath("$.order.submitedTime").isNotEmpty())
        .andExpect(jsonPath("$.order.deliveryTime").isNotEmpty())
        .andExpect(jsonPath("$.order.riderId", is((int)rider.getId())))
        .andExpect(jsonPath("$.order.storeId", is((int)store.getId())))
        .andExpect(jsonPath("$.order.orderDetails", is("details")))
        .andExpect(jsonPath("$.order.phone", is("1234")))
        .andExpect(jsonPath("$.order.rating").isEmpty())
        .andExpect(jsonPath("$.rider.id", is((int)rider.getId())))
        .andExpect(jsonPath("$.rider.ratingMean", is(0.0)))
        .andExpect(jsonPath("$.rider.phone", is("1234")));
    }
}
