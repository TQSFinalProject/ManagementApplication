package com.tqs.trackit.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import com.tqs.trackit.model.Rider;

@DataJpaTest
public class RiderRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RiderRepository riderRepository;

    @Test
    void whenFindRiderByExistingId_thenReturnRider() {
        List<Double> ratings = new ArrayList<>();
        ratings.add(4.5);
        Rider rider1 = new Rider("Miguel","Ferreira","937485748","miguelf","password","link",49.4578,76.93284,ratings);
        entityManager.persistAndFlush(rider1);

        Rider fromDb = riderRepository.findById(rider1.getId()).orElse(null);
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getFirstName()).isEqualTo(rider1.getFirstName());
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        Rider fromDb = riderRepository.findById(-111L).orElse(null);
        assertThat(fromDb).isNull();
    }
    
}
