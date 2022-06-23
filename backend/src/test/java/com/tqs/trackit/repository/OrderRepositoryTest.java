package com.tqs.trackit.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.tqs.trackit.model.Order;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@DataJpaTest
public class OrderRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void whenFindOrderByExistingId_thenReturnOrder() {
        Order order1 = new Order("Late","Home Y", 10.0, 10.0, LocalDateTime.of(2022,Month.JANUARY,7,19,43,20),LocalDateTime.of(2022,Month.JANUARY,7,19,20,10),LocalDateTime.of(2022,Month.JANUARY,7,19,45,32),1L,1L,"Wine X","9183725364",4.5);
        entityManager.persistAndFlush(order1);

        Order fromDb = orderRepository.findById(order1.getId()).orElse(null);
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getOrderStatus()).isEqualTo(order1.getOrderStatus());
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        Order fromDb = orderRepository.findById(-111L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void whenFindOrderByExistingRiderId_thenReturnListOfOrders() {
        Pageable elements = PageRequest.of(0, 4);
        Order order1 = new Order("Late", "Home Y", 10.0, 10.0, LocalDateTime.of(2022,Month.JANUARY,7,19,43,20),LocalDateTime.of(2022,Month.JANUARY,7,19,20,10),LocalDateTime.of(2022,Month.JANUARY,7,19,45,32),1L,1L,"Wine X","9183725364",4.5);
        Order order2 = new Order("On Time", "Home X", 10.0, 10.0, LocalDateTime.of(2022, Month.JANUARY, 7, 15, 43, 00), LocalDateTime.of(2022, Month.JANUARY, 7, 15, 30, 10), LocalDateTime.of(2022, Month.JANUARY, 7, 15, 35, 10), 1L, 1L, "Wine Y", "9183725354", 4.0);

        entityManager.persistAndFlush(order1);
        entityManager.persistAndFlush(order2);

        Page<Order> pageFromDb = orderRepository.findByRiderId(1L,elements);
        List<Order> fromDb = pageFromDb.getContent();
        assertThat(fromDb.size()).isEqualTo(2);
        assertThat(fromDb.get(0).getOrderDetails()).isEqualTo(order1.getOrderDetails());
        assertThat(fromDb.get(1).getOrderDetails()).isEqualTo(order2.getOrderDetails());
    }

    @Test
    void whenInvalidRiderId_thenReturnEmptyList() {
        Pageable elements = PageRequest.of(0, 4);
        Page<Order> pageFromDb = orderRepository.findByRiderId(-111L,elements);
        List<Order> fromDb = pageFromDb.getContent();
        assertThat(fromDb.size()).isEqualTo(0);
    }

    @Test
    void whenFindOrderByExistingStoreId_thenReturnListOfOrders() {
        Pageable elements = PageRequest.of(0, 4);
        Order order1 = new Order("Late","Home Y",10.0,10.0, LocalDateTime.of(2022,Month.JANUARY,7,19,43,20),LocalDateTime.of(2022,Month.JANUARY,7,19,20,10),LocalDateTime.of(2022,Month.JANUARY,7,19,45,32),1L,1L,"Wine X","9183725364",4.5);
        Order order2 = new Order("On Time", "Home X",10.0,10.0, LocalDateTime.of(2022, Month.JANUARY, 7, 15, 43, 00), LocalDateTime.of(2022, Month.JANUARY, 7, 15, 30, 10), LocalDateTime.of(2022, Month.JANUARY, 7, 15, 35, 10), 1L, 1L, "Wine Y", "9183725354", 4.0);

        entityManager.persistAndFlush(order1);
        entityManager.persistAndFlush(order2);

        Page<Order> pageFromDb = orderRepository.findByStoreId(1L,elements);
        List<Order> fromDb = pageFromDb.getContent();
        assertThat(fromDb.size()).isEqualTo(2);
        assertThat(fromDb.get(0).getOrderDetails()).isEqualTo(order1.getOrderDetails());
        assertThat(fromDb.get(1).getOrderDetails()).isEqualTo(order2.getOrderDetails());
    }

    @Test
    void whenInvalidStoreId_thenReturnEmptyList() {
        Pageable elements = PageRequest.of(0, 4);
        Page<Order> pageFromDb = orderRepository.findByStoreId(-111L,elements);
        List<Order> fromDb = pageFromDb.getContent();
        assertThat(fromDb.size()).isEqualTo(0);
    }

    @Test
    void whenFindOrderByExistingOrderStatus_thenReturnListOfOrders() {
        Pageable elements = PageRequest.of(0, 4);
        Order order1 = new Order("Late","Home Y",10.0,10.0, LocalDateTime.of(2022,Month.JANUARY,7,19,43,20),LocalDateTime.of(2022,Month.JANUARY,7,19,20,10),LocalDateTime.of(2022,Month.JANUARY,7,19,45,32),1L,1L,"Wine X","9183725364",4.5);
        Order order2 = new Order("On Time", "Home X",10.0,10.0, LocalDateTime.of(2022, Month.JANUARY, 7, 15, 43, 00), LocalDateTime.of(2022, Month.JANUARY, 7, 15, 30, 10), LocalDateTime.of(2022, Month.JANUARY, 7, 15, 35, 10), 1L, 1L, "Wine Y", "9183725354", 4.0);

        entityManager.persistAndFlush(order1);
        entityManager.persistAndFlush(order2);

        Page<Order> pageFromDb = orderRepository.findByOrderStatus("Late",elements);
        List<Order> fromDb = pageFromDb.getContent();
        assertThat(fromDb.size()).isEqualTo(1);
        assertThat(fromDb.get(0)).isEqualTo(order1);
    }

    @Test
    void whenInvalidOrderStatus_thenReturnEmptyList() {
        Pageable elements = PageRequest.of(0, 4);
        Page<Order> pageFromDb = orderRepository.findByOrderStatus("Delivered",elements);
        List<Order> fromDb = pageFromDb.getContent();
        assertThat(fromDb.size()).isEqualTo(0);
    }

    @Test
    void whenDelete1OrderById_thenReturnListOfOrders() {
        Pageable elements = PageRequest.of(0, 4);
        Order order1 = new Order("Late","Home Y", 10.0,10.0,LocalDateTime.of(2022,Month.JANUARY,7,19,43,20),LocalDateTime.of(2022,Month.JANUARY,7,19,20,10),LocalDateTime.of(2022,Month.JANUARY,7,19,45,32),1L,1L,"Wine X","9183725364",4.5);
        Order order2 = new Order("On Time", "Home X",10.0,10.0, LocalDateTime.of(2022, Month.JANUARY, 7, 15, 43, 00), LocalDateTime.of(2022, Month.JANUARY, 7, 15, 30, 10), LocalDateTime.of(2022, Month.JANUARY, 7, 15, 35, 10), 1L, 1L, "Wine Y", "9183725354", 4.0);
        Order order3 = new Order("On Time", "Home Z",10.0,10.0, LocalDateTime.of(2022, Month.JANUARY, 7, 15, 43, 00), LocalDateTime.of(2022, Month.JANUARY, 7, 15, 30, 10), LocalDateTime.of(2022, Month.JANUARY, 7, 15, 35, 10), 2L, 1L, "Wine Z", "9183725354", 4.0);

        entityManager.persistAndFlush(order1);
        entityManager.persistAndFlush(order2);
        entityManager.persistAndFlush(order3);

        orderRepository.deleteById(order1.getId());

        Page<Order> pageFromDb = orderRepository.findAll(elements);
        List<Order> fromDb = pageFromDb.getContent();
        assertThat(fromDb.size()).isEqualTo(2);
        assertThat(fromDb.get(0).getDeliveryAddress()).isEqualTo(order2.getDeliveryAddress());
        assertThat(fromDb.get(1).getDeliveryAddress()).isEqualTo(order3.getDeliveryAddress());
    }
}
