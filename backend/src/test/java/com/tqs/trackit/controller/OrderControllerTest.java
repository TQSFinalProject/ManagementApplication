package com.tqs.trackit.controller;

import org.junit.jupiter.api.BeforeEach;
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
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    @BeforeEach
    public void resetDb() {
        orderRepository.deleteAll();
    }

    @Test
     void whenValidInput_thenCreateOrder() throws IOException, Exception {
        Order order1 = new Order("Late", "Home Y", 10.0, 10.0, LocalDateTime.of(2022,Month.JANUARY,7,19,43,20),LocalDateTime.of(2022,Month.JANUARY,7,19,20,10),LocalDateTime.of(2022,Month.JANUARY,7,19,45,32),1L,1L,"Wine X","9183725364",4.5);

        mvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(order1)));

        List<Order> found = orderRepository.findAll();
        assertThat(found).extracting(Order::getOrderStatus).containsOnly("Late");
    }

    @Test
     void givenOrders_whenGetOrders_thenStatus200FromPage0() throws Exception {
        Order order1 = new Order("Late", "Home Y", 10.0, 10.0, LocalDateTime.of(2022, Month.JANUARY, 7, 19, 43, 20),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 20, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 45, 32), 1L, 1L, "Wine X", "9183725364", 4.5);
        Order order2 = new Order("On Time", "Home X", 10.0, 10.0, LocalDateTime.of(2022, Month.JANUARY, 7, 15, 43, 00),
                LocalDateTime.of(2022, Month.JANUARY, 7, 15, 30, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 15, 35, 10), 1L, 1L, "Wine X", "9183725354", 4.0);
        orderRepository.saveAndFlush(order1);
        orderRepository.saveAndFlush(order2);

        mvc.perform(get("/api/orders?page=0").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(equalTo(2))))
                .andExpect(jsonPath("$.content[0].orderStatus", is("Late")))
                .andExpect(jsonPath("$.content[1].orderStatus", is("On Time")));
    }

    @Test
     void givenOrders_whenGetOrders_thenStatus200FromPage1() throws Exception {
        Order order1 = new Order("Late", "Home Y", 10.0, 10.0, LocalDateTime.of(2022, Month.JANUARY, 7, 19, 43, 20),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 20, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 45, 32), 1L, 1L, "Wine X", "9183725364", 4.5);
        Order order2 = new Order("On Time", "Home X", 10.0, 10.0, LocalDateTime.of(2022, Month.JANUARY, 7, 15, 43, 00),
                LocalDateTime.of(2022, Month.JANUARY, 7, 15, 30, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 15, 35, 10), 1L, 1L, "Wine X", "9183725354", 4.0);
        orderRepository.saveAndFlush(order1);
        orderRepository.saveAndFlush(order2);

        mvc.perform(get("/api/orders?page=1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(equalTo(0))));
    }

    @Test
    void givenOrderId_whenGetOrderById_thenStatus200() throws Exception {
        Order order1 = new Order("Late", "Home Y", 10.0, 10.0, LocalDateTime.of(2022, Month.JANUARY, 7, 19, 43, 20),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 20, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 45, 32), 1L, 1L, "Wine X", "9183725364", 4.5);

        orderRepository.saveAndFlush(order1);

        mvc.perform(get("/api/orders/{orderId}",order1.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", equalTo(13)))
                .andExpect(jsonPath("$.orderStatus", is("Late")));
    }

    @Test
    void givenRiderId_whenGetOrdersByRiderId_thenStatus200() throws Exception {
        Order order1 = new Order("Late", "Home Y", 10.0, 10.0, LocalDateTime.of(2022, Month.JANUARY, 7, 19, 43, 20),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 20, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 45, 32), 1L, 1L, "Wine X", "9183725364", 4.5);
        Order order2 = new Order("On Time", "Home X", 10.0, 10.0, LocalDateTime.of(2022, Month.JANUARY, 7, 15, 43, 00),
                LocalDateTime.of(2022, Month.JANUARY, 7, 15, 30, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 15, 35, 10), 1L, 1L, "Wine Y", "9183725354", 4.0);
        Order order3 = new Order("On Time", "Home Z", 10.0, 10.0, LocalDateTime.of(2022, Month.JANUARY, 7, 15, 43, 00), LocalDateTime.of(2022, Month.JANUARY, 7, 15, 30, 10), LocalDateTime.of(2022, Month.JANUARY, 7, 15, 35, 10), 2L, 1L, "Wine Z", "9183725354", 4.0);
        order1.setId(1L);
        orderRepository.saveAndFlush(order1);
        orderRepository.saveAndFlush(order2);
        orderRepository.saveAndFlush(order3);

        mvc.perform(get("/api/orders/rider/{riderId}",1L).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(equalTo(2))))
                .andExpect(jsonPath("$.content[0].orderDetails", is("Wine X")))
                .andExpect(jsonPath("$.content[1].orderDetails", is("Wine Y")));


    }

    @Test
    void givenStoreId_whenGetOrdersByStoreId_thenStatus200() throws Exception {
        Order order1 = new Order("Late", "Home Y",10.0,10.0, LocalDateTime.of(2022, Month.JANUARY, 7, 19, 43, 20),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 20, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 45, 32), 1L, 1L, "Wine X", "9183725364", 4.5);
        Order order2 = new Order("On Time", "Home X",10.0,10.0, LocalDateTime.of(2022, Month.JANUARY, 7, 15, 43, 00),
                LocalDateTime.of(2022, Month.JANUARY, 7, 15, 30, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 15, 35, 10), 1L, 1L, "Wine Y", "9183725354", 4.0);
        Order order3 = new Order("On Time", "Home Z",10.0,10.0, LocalDateTime.of(2022, Month.JANUARY, 7, 15, 43, 00), LocalDateTime.of(2022, Month.JANUARY, 7, 15, 30, 10), LocalDateTime.of(2022, Month.JANUARY, 7, 15, 35, 10), 2L, 1L, "Wine Z", "9183725354", 4.0);
        order1.setId(1L);
        orderRepository.saveAndFlush(order1);
        orderRepository.saveAndFlush(order2);
        orderRepository.saveAndFlush(order3);

        mvc.perform(get("/api/orders/store/{storeId}",1L).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(equalTo(3))))
                .andExpect(jsonPath("$.content[0].orderDetails", is("Wine X")))
                .andExpect(jsonPath("$.content[1].orderDetails", is("Wine Y")))
                .andExpect(jsonPath("$.content[2].orderDetails", is("Wine Z")));
    }

    @Test
    void givenStatus_whenGetOrdersByStatus_thenStatus200() throws Exception {
        Order order1 = new Order("Late", "Home Y",10.0,10.0, LocalDateTime.of(2022, Month.JANUARY, 7, 19, 43, 20),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 20, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 45, 32), 1L, 1L, "Wine X", "9183725364", 4.5);
        Order order2 = new Order("On Time", "Home X",10.0,10.0, LocalDateTime.of(2022, Month.JANUARY, 7, 15, 43, 00),
                LocalDateTime.of(2022, Month.JANUARY, 7, 15, 30, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 15, 35, 10), 1L, 1L, "Wine Y", "9183725354", 4.0);
        Order order3 = new Order("On Time", "Home Z",10.0,10.0, LocalDateTime.of(2022, Month.JANUARY, 7, 15, 43, 00), LocalDateTime.of(2022, Month.JANUARY, 7, 15, 30, 10), LocalDateTime.of(2022, Month.JANUARY, 7, 15, 35, 10), 2L, 1L, "Wine Z", "9183725354", 4.0);
        order1.setId(1L);
        orderRepository.saveAndFlush(order1);
        orderRepository.saveAndFlush(order2);
        orderRepository.saveAndFlush(order3);

        mvc.perform(get("/api/orders/status/{status}","On Time").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(equalTo(2))))
                .andExpect(jsonPath("$.content[0].orderDetails", is("Wine Y")))
                .andExpect(jsonPath("$.content[1].orderDetails", is("Wine Z")));
    }

    @Test
    void whenDeleteOrderById_thenStatus200() throws Exception {
        Order order1 = new Order("Late", "Home Y",10.0,10.0, LocalDateTime.of(2022, Month.JANUARY, 7, 19, 43, 20),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 20, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 45, 32), 1L, 1L, "Wine X", "9183725364", 4.5);
        orderRepository.saveAndFlush(order1);

        mvc.perform(delete("/api/orders/{orderId}",order1.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Deleted")));
    }

    @Test
    void whenDeleteOrderByMalformedId_thenStatus400() throws Exception {
        Order order1 = new Order("Late", "Home Y",10.0,10.0, LocalDateTime.of(2022, Month.JANUARY, 7, 19, 43, 20),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 20, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 45, 32), 1L, 1L, "Wine X", "9183725364", 4.5);
        order1.setId(1L);
        orderRepository.saveAndFlush(order1);

        mvc.perform(delete("/api/orders/{orderId}","NotAnId").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Not a Valid ID")));
    }

    @Test
    void whenDeleteOrderByNonExistentId_thenStatus500() throws Exception {
        Order order1 = new Order("Late", "Home Y",10.0,10.0, LocalDateTime.of(2022, Month.JANUARY, 7, 19, 43, 20),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 20, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 45, 32), 1L, 1L, "Wine X", "9183725364", 4.5);
        order1.setId(1L);
        orderRepository.saveAndFlush(order1);

        mvc.perform(delete("/api/orders/{orderId}","5").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

}
