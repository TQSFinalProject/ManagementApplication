package com.tqs.trackit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

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

        order1.setId(10L);

        List<Order> allOrders = Arrays.asList(order1, order2);

        Mockito.when(orderRepository.findById(order1.getId())).thenReturn(Optional.of(order1));
        Mockito.when(orderRepository.findById(-1L)).thenReturn(Optional.empty());
        Mockito.when(orderRepository.findAll()).thenReturn(allOrders);
    }
    
    @Test
    void whenValidId_thenOrderShouldBeFound() {
        Order fromDb = orderService.getOrderById(10L);
        assertThat(fromDb.getOrderStatus()).isEqualTo("Late");
        verifyFindByIdIsCalledOnce();
    }

    @Test
    void whenInValidId_thenOrderShouldNotBeFound() {
        Order fromDb = orderService.getOrderById(-1L);
        verifyFindByIdIsCalledOnce();
        assertThat(fromDb).isNull();
    }

    @Test
    void given2Orders_whenGetAllOrders_thenReturn3Orders() {
        Order order1 = new Order("Late", "Home Y", LocalDateTime.of(2022, Month.JANUARY, 7, 19, 43, 20),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 20, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 19, 45, 32), 1L, 1L, "Wine X", "9183725364", 4.5);
        Order order2 = new Order("On Time", "Home X", LocalDateTime.of(2022, Month.JANUARY, 7, 15, 43, 00),
                LocalDateTime.of(2022, Month.JANUARY, 7, 15, 30, 10),
                LocalDateTime.of(2022, Month.JANUARY, 7, 15, 35, 10), 1L, 1L, "Wine X", "9183725354", 4.0);

        List<Order> allOrders = orderService.getOrders();
        verifyFindOrdersIsCalledOnce();
        assertThat(allOrders).hasSize(2).extracting(Order::getDeliveryAddress).contains(order1.getDeliveryAddress(), order2.getDeliveryAddress());

    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(orderRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
    }

    private void verifyFindOrdersIsCalledOnce() {
        Mockito.verify(orderRepository, VerificationModeFactory.times(1)).findAll();
    }

}
