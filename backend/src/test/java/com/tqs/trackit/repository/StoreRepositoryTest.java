package com.tqs.trackit.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.tqs.trackit.model.Store;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StoreRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StoreRepository storeRepository;

    @Test
    void whenFindStoreByExistingId_thenReturnStore() {
        Store store1 = new Store("Store X",2.5,"Avenue X","passwordX");
        entityManager.persistAndFlush(store1);

        Store fromDb = storeRepository.findById(store1.getId()).orElse(null);
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getStoreName()).isEqualTo(store1.getStoreName());
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        Store fromDb = storeRepository.findById(-111L).orElse(null);
        assertThat(fromDb).isNull();
    }
    
}
