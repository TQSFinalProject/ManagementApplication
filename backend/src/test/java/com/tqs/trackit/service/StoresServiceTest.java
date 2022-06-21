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

import com.tqs.trackit.model.Store;
import com.tqs.trackit.repository.StoreRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StoresServiceTest {

    @Mock(lenient = true)
    private StoreRepository storeRepository;

    @InjectMocks
    private StoresService storeService;

    @BeforeEach
    public void setUp() {
        Store store1 = new Store("Store X",2.5,"Avenue X", 10.0, 10.0,"X","X");
        Store store2 = new Store("Store Y",3.0,"Avenue Y", 10.0, 10.0,"X","X");
        Store store3 = new Store("Store Z",4.5,"Avenue Z", 10.0, 10.0,"X","X");

        store1.setId(10L);

        List<Store> allStores = Arrays.asList(store1, store2, store3);

        Page<Store> page = new PageImpl<>(allStores);

        Mockito.when(storeRepository.findById(store1.getId())).thenReturn(Optional.of(store1));
        Mockito.when(storeRepository.findById(-1L)).thenReturn(Optional.empty());
        Mockito.when(storeRepository.findAll(PageRequest.of(0, 6))).thenReturn(page);
        Mockito.when(storeRepository.findByStoreName(store1.getStoreName())).thenReturn(store1);
        Mockito.when(storeRepository.findByStoreAddress(store1.getStoreAddress())).thenReturn(store1);
    }

    @Test
    void whenValidId_thenStoreShouldBeFound() {
        Store store1 = new Store("Store X",2.5,"Avenue X", 10.0, 10.0,"X","X");
        store1.setId(10L);
        Store fromDb = storeService.getStoreById(store1.getId());
        assertThat(fromDb).isEqualTo(store1);
        verifyFindByIdIsCalledOnce();
    }

    @Test
    void whenInvalidId_thenStoreShouldNotBeFound() {
        Store fromDb = storeService.getStoreById(-1L);
        verifyFindByIdIsCalledOnce();
        assertThat(fromDb).isNull();
    }

    @Test
    void given3Stores_whenGetAllStores_thenReturn3Stores() {
        Store store1 = new Store("Store X",2.5,"Avenue X", 10.0, 10.0,"X","X");
        Store store2 = new Store("Store Y",3.0,"Avenue Y", 10.0, 10.0,"Y","X");
        Store store3 = new Store("Store Z",4.5,"Avenue Z", 10.0, 10.0,"Z","X");
        

        Page<Store> allStores = storeService.getStores(0);
        List<Store> elements = allStores.getContent();
        verifyFindStoresIsCalledOnce();
        assertThat(elements).hasSize(3).extracting(Store::getStoreName).contains(store1.getStoreName(),store2.getStoreName(),store3.getStoreName());

    }

    @Test
    void given1Store_whenGetStoreByName_thenReturnStoreWithThatName() {
        Store store1 = new Store("Store X",2.5,"Avenue X", 10.0, 10.0,"X","X");
        store1.setId(10L);

        Store fromServ = storeService.getStoreByName(store1.getStoreName());
        verifyFindByNameIsCalledOnce();
        assertThat(fromServ).isEqualTo(store1);

    }

    @Test
    void given1Store_whenGetStoreByAddress_thenReturnStoreWithThatAddress() {
        Store store1 = new Store("Store X",2.5,"Avenue X", 10.0, 10.0,"X","X");
        store1.setId(10L);
        
        Store fromServ = storeService.getStoreByAddress(store1.getStoreAddress());
        verifyFindByAddressIsCalledOnce();
        assertThat(fromServ).isEqualTo(store1);

    }



    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(storeRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
    }

    private void verifyFindByNameIsCalledOnce() {
        Mockito.verify(storeRepository, VerificationModeFactory.times(1)).findByStoreName(Mockito.anyString());
    }

    private void verifyFindByAddressIsCalledOnce() {
        Mockito.verify(storeRepository, VerificationModeFactory.times(1)).findByStoreAddress(Mockito.anyString());
    }

    private void verifyFindStoresIsCalledOnce() {
        Mockito.verify(storeRepository, VerificationModeFactory.times(1)).findAll(PageRequest.of(0, 6));
    }
    
}
