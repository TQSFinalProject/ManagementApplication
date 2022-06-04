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
import com.tqs.trackit.repository.OrderRepository;

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
public class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private OrderRepository orderRepository;

    @AfterEach
    public void resetDb() {
        orderRepository.deleteAll();
    }

    @Test
     void whenValidInput_thenCreateOrder() throws IOException, Exception {
        Order order1 = new Order("Late","Home Y", LocalDateTime.of(2022,Month.JANUARY,7,19,43,20),LocalDateTime.of(2022,Month.JANUARY,7,19,20,10),LocalDateTime.of(2022,Month.JANUARY,7,19,45,32),1L,1L,"Wine X","9183725364",4.5);

        mvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(order1)));

        List<Order> found = orderRepository.findAll();
        assertThat(found).extracting(Order::getOrderStatus).containsOnly("Late");
    }

    @Test
     void givenOrders_whenGetOrders_thenStatus200() throws Exception {
        Order order1 = new Order("Late", "Home Y", LocalDateTime.of(2022, Month.JANUARY, 7, 19, 43, 20),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 20, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 45, 32), 1L, 1L, "Wine X", "9183725364", 4.5);
        Order order2 = new Order("On Time", "Home X", LocalDateTime.of(2022, Month.JANUARY, 7, 15, 43, 00),
                LocalDateTime.of(2022, Month.JANUARY, 7, 15, 30, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 15, 35, 10), 1L, 1L, "Wine X", "9183725354", 4.0);
        orderRepository.saveAndFlush(order1);
        orderRepository.saveAndFlush(order2);

        mvc.perform(get("/api/orders").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(equalTo(2))))
                .andExpect(jsonPath("$[0].orderStatus", is("Late")))
                .andExpect(jsonPath("$[1].orderStatus", is("On Time")));
    }


    
}
