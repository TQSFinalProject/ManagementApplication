package com.tqs.trackit.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.tqs.trackit.model.Store;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
public class StoreRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StoreRepository storeRepository;

    @Test
    void whenFindStoreByExistingId_thenReturnStore() {
        Store store1 = new Store("Store X",2.5,"Avenue X", 10.0, 10.0,"X","X");
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

    @Test
    void whenDelete1StoreById_thenReturnListOfStores() {
        Pageable elements = PageRequest.of(0, 6);
        Store store1 = new Store("Store X",2.5,"Avenue X",10.0,10.0,"X","X");
        entityManager.persistAndFlush(store1);

        storeRepository.deleteById(store1.getId());

        Page<Store> pageFromDb = storeRepository.findAll(elements);
        List<Store> fromDb = pageFromDb.getContent();
        assertThat(fromDb.size()).isEqualTo(0);
    }
    
}
