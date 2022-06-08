package com.tqs.trackit.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.tqs.trackit.model.Order;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class OrderRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void whenFindOrderByExistingId_thenReturnOrder() {
        Order order1 = new Order("Late","Home Y", LocalDateTime.of(2022,Month.JANUARY,7,19,43,20),LocalDateTime.of(2022,Month.JANUARY,7,19,20,10),LocalDateTime.of(2022,Month.JANUARY,7,19,45,32),1L,1L,"Wine X","9183725364",4.5);
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
        Order order1 = new Order("Late","Home Y", LocalDateTime.of(2022,Month.JANUARY,7,19,43,20),LocalDateTime.of(2022,Month.JANUARY,7,19,20,10),LocalDateTime.of(2022,Month.JANUARY,7,19,45,32),1L,1L,"Wine X","9183725364",4.5);
        Order order2 = new Order("On Time", "Home X", LocalDateTime.of(2022, Month.JANUARY, 7, 15, 43, 00), LocalDateTime.of(2022, Month.JANUARY, 7, 15, 30, 10), LocalDateTime.of(2022, Month.JANUARY, 7, 15, 35, 10), 1L, 1L, "Wine Y", "9183725354", 4.0);

        entityManager.persistAndFlush(order1);
        entityManager.persistAndFlush(order2);

        List<Order> fromDb = orderRepository.findByRiderId(1L);
        assertThat(fromDb.size()).isEqualTo(2);
        assertThat(fromDb.get(0).getOrderDetails()).isEqualTo("Wine X");
        assertThat(fromDb.get(1).getOrderDetails()).isEqualTo("Wine Y");
    }

    @Test
    void whenInvalidRiderId_thenReturnEmptyList() {
        List<Order> fromDb = orderRepository.findByRiderId(-111L);
        assertThat(fromDb.size()).isEqualTo(0);
    }
}
