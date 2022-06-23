package com.tqs.trackit.unit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.tqs.trackit.model.JobApplication;
import com.tqs.trackit.dtos.JobApplicationDTO;
import com.tqs.trackit.model.Order;
import com.tqs.trackit.dtos.OrderDTO;
import com.tqs.trackit.model.Rider;
import com.tqs.trackit.dtos.RiderDTO;
import com.tqs.trackit.model.Store;
import com.tqs.trackit.dtos.StoreDTO;

import static org.assertj.core.api.Assertions.assertThat;

public class DTOTest {
    @Test
    void checkIfOrderIsEqualToDTOConvertedOrder() {
        Order order1 = new Order("Late", "Home Y", 10.0, 10.0, LocalDateTime.of(2022,Month.JANUARY,7,19,43,20),LocalDateTime.of(2022,Month.JANUARY,7,19,20,10),LocalDateTime.of(2022,Month.JANUARY,7,19,45,32),1L,1L,"Wine X","9183725364",4.5);
        Order orderFromDTO = OrderDTO.fromOrderEntity(order1).toOrderEntity();
        assertThat(orderFromDTO).isEqualTo(order1);
    }

    @Test
    void checkIfStoreIsEqualToDTOConvertedStore() {
        Store store1 = new Store("Store X",2.5,"Avenue X", 10.0, 10.0,"X","X");
        Store storeFromDTO = StoreDTO.fromStoreEntity(store1).toStoreEntity();
        assertThat(storeFromDTO).isEqualTo(store1);
    }

    @Test
    void checkIfRiderIsEqualToDTOConvertedRider() {
        List<Double> ratings = new ArrayList<>();
        ratings.add(4.5);
        ratings.add(4.0);
        Rider rider1 = new Rider("Miguel","Ferreira","937485748","miguelf","password","link",49.4578,76.93284,ratings);
        Rider riderFromDTO = RiderDTO.fromRiderEntity(rider1).toRiderEntity();
        assertThat(riderFromDTO).isEqualTo(rider1);
    }

    @Test
    void checkIfJobApplicationIsEqualToDTOConvertedJobApplication() {
        JobApplication jobApp1 = new JobApplication("Paulo","Silva",LocalDate.of(1984, 2, 3),"943526152","paulo.silva@ua.pt","link_to_photo","link_to_cv");
        JobApplication jobApplicationFromDTO = JobApplicationDTO.fromJobApplicationEntity(jobApp1).toJobApplicationEntity();
        assertThat(jobApplicationFromDTO).isEqualTo(jobApp1);
    }
}
