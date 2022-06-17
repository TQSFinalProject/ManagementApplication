package com.tqs.trackit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.tqs.trackit.model.Order;
import com.tqs.trackit.repository.OrderRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OrdersServiceTest {

    @Mock(lenient = true)
    private OrderRepository orderRepository;

    @InjectMocks
    private OrdersService orderService;

    @BeforeEach
    public void setUp() {

        Order order1 = new Order("Late", "Home Y", LocalDateTime.of(2022, Month.JANUARY, 7, 19, 43, 20),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 20, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 45, 32), 1L, 1L, "Wine X", "9183725364", 4.5);
        Order order2 = new Order("On Time", "Home X", LocalDateTime.of(2022, Month.JANUARY, 7, 15, 43, 00),
                LocalDateTime.of(2022, Month.JANUARY, 7, 15, 30, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 15, 35, 10), 1L, 1L, "Wine X", "9183725354", 4.0);
        Order order3 = new Order("On Time", "Home Z", LocalDateTime.of(2022, Month.JANUARY, 7, 15, 43, 00), LocalDateTime.of(2022, Month.JANUARY, 7, 15, 30, 10), LocalDateTime.of(2022, Month.JANUARY, 7, 15, 35, 10), 2L, 1L, "Wine Z", "9183725354", 4.0);
        order1.setId(10L);

        List<Order> allOrders = Arrays.asList(order1, order2,order3);

        Mockito.when(orderRepository.findById(order1.getId())).thenReturn(Optional.of(order1));
        Mockito.when(orderRepository.findById(-1L)).thenReturn(Optional.empty());
        Mockito.when(orderRepository.findAll(PageRequest.of(0, 4))).thenReturn(new PageImpl<>(allOrders));
        Mockito.when(orderRepository.findByRiderId(1L,PageRequest.of(0, 4))).thenReturn(new PageImpl<>(Arrays.asList(order1,order2)));
    }
    
    @Test
    void whenValidId_thenOrderShouldBeFound() {
        Order fromDb = orderService.getOrderById(10L);
        assertThat(fromDb.getOrderStatus()).isEqualTo("Late");
        verifyFindByIdIsCalledOnce();
    }

    @Test
    void whenInvalidId_thenOrderShouldNotBeFound() {
        Order fromDb = orderService.getOrderById(-1L);
        verifyFindByIdIsCalledOnce();
        assertThat(fromDb).isNull();
    }

    @Test
    void given3Orders_whenGetAllOrders_thenRetur3Orders() {
        Order order1 = new Order("Late", "Home Y", LocalDateTime.of(2022, Month.JANUARY, 7, 19, 43, 20),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 20, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 45, 32), 1L, 1L, "Wine X", "9183725364", 4.5);
        Order order2 = new Order("On Time", "Home X", LocalDateTime.of(2022, Month.JANUARY, 7, 15, 43, 00),
                LocalDateTime.of(2022, Month.JANUARY, 7, 15, 30, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 15, 35, 10), 1L, 1L, "Wine X", "9183725354", 4.0);
        Order order3 = new Order("On Time", "Home Z", LocalDateTime.of(2022, Month.JANUARY, 7, 15, 43, 00), LocalDateTime.of(2022, Month.JANUARY, 7, 15, 30, 10), LocalDateTime.of(2022, Month.JANUARY, 7, 15, 35, 10), 2L, 1L, "Wine Z", "9183725354", 4.0);
        Page<Order> allOrders = orderService.getOrders(0);
        verifyFindOrdersIsCalledOnce();
        assertThat(allOrders.getContent()).hasSize(3).extracting(Order::getDeliveryAddress).contains(order1.getDeliveryAddress(), order2.getDeliveryAddress(),order3.getDeliveryAddress());

    }

    @Test
    void given2Orders_whenGetAllOrdersbyRiderId_thenReturnRiderIdOrders() {
        Order order1 = new Order("Late", "Home Y", LocalDateTime.of(2022, Month.JANUARY, 7, 19, 43, 20),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 20, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 45, 32), 1L, 1L, "Wine X", "9183725364", 4.5);
        Order order2 = new Order("On Time", "Home X", LocalDateTime.of(2022, Month.JANUARY, 7, 15, 43, 00),
                LocalDateTime.of(2022, Month.JANUARY, 7, 15, 30, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 15, 35, 10), 1L, 1L, "Wine X", "9183725354", 4.0);

        Page<Order> riderOrders = orderService.getOrdersByRiderId(1L,0);
        verifyFindOrdersByRiderIdIsCalledOnce();
        assertThat(riderOrders.getContent()).hasSize(2).extracting(Order::getOrderDetails).contains(order1.getOrderDetails(), order2.getOrderDetails());

    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(orderRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
    }

    private void verifyFindOrdersIsCalledOnce() {
        Mockito.verify(orderRepository, VerificationModeFactory.times(1)).findAll(PageRequest.of(0, 4));
    }

    private void verifyFindOrdersByRiderIdIsCalledOnce() {
        Mockito.verify(orderRepository, VerificationModeFactory.times(1)).findByRiderId(1L,(PageRequest.of(0, 4)));
    }

}
