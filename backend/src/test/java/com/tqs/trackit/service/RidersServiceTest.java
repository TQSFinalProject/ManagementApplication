package com.tqs.trackit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tqs.trackit.model.Rider;
import com.tqs.trackit.repository.RiderRepository;



import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RidersServiceTest {
    @Mock(lenient = true)
    private RiderRepository riderRepository;

    @InjectMocks
    private RidersService riderService;

    @BeforeEach
    public void setUp() {
        List<Double> ratings1 = new ArrayList<>();
        ratings1.add(4.5);
        ratings1.add(4.0);
        List<Double> ratings2 = new ArrayList<>();
        ratings2.add(2.5);
        ratings2.add(3.5);
        List<Double> ratings3 = new ArrayList<>();
        ratings3.add(5.0);
        ratings3.add(3.0);
        Rider rider1 = new Rider("Miguel","Ferreira","937485748","miguelf","password","link",49.4578,76.93284,ratings1);
        Rider rider2 = new Rider("Afonso","Campos","937451448","afonsoc","password","link",49.4455,32.93284,ratings2);
        Rider rider3 = new Rider("Ana","Monteiro","9153726384","anam","password","link",39.4455,12.93284,ratings3);

        rider1.setId(10L);

        List<Rider> allRiders = Arrays.asList(rider1, rider2, rider3);

        Mockito.when(riderRepository.findById(rider1.getId())).thenReturn(Optional.of(rider1));
        Mockito.when(riderRepository.findById(-1L)).thenReturn(Optional.empty());
        Mockito.when(riderRepository.findAll()).thenReturn(allRiders);
    }

    @Test
    void whenValidId_thenRiderShouldBeFound() {
        Rider fromDb = riderService.getRiderById(10L);
        assertThat(fromDb.getFirstName()).isEqualTo("Miguel");
        verifyFindByIdIsCalledOnce();
    }

    @Test
    void whenInvalidId_thenRiderShouldNotBeFound() {
        Rider fromDb = riderService.getRiderById(-1L);
        verifyFindByIdIsCalledOnce();
        assertThat(fromDb).isNull();
    }

    @Test
    void given3Riders_whenGetAllRiders_thenReturn3Riders() {
        List<Double> ratings = new ArrayList<>();
        ratings.add(4.5);
        ratings.add(4.0);
        Rider rider1 = new Rider("Miguel","Ferreira","937485748","miguelf","password","link",49.4578,76.93284,ratings);
        Rider rider2 = new Rider("Afonso","Campos","937451448","afonsoc","password","link",49.4455,32.93284,ratings);
        Rider rider3 = new Rider("Ana","Monteiro","9153726384","anam","password","link",39.4455,12.93284,ratings);

        List<Rider> allRiders = riderService.getRiders();
        verifyFindRidersIsCalledOnce();
        assertThat(allRiders).hasSize(3).extracting(Rider::getFirstName).contains(rider1.getFirstName(), rider2.getFirstName(), rider3.getFirstName());

    }

    @Test
    void whenGetAllRidersOrderedAlphabetically_thenReturnAllRidersOrderedAlphabetically() {
        List<Double> ratings1 = new ArrayList<>();
        ratings1.add(4.5);
        ratings1.add(4.0);
        List<Double> ratings2 = new ArrayList<>();
        ratings2.add(2.5);
        ratings2.add(3.5);
        List<Double> ratings3 = new ArrayList<>();
        ratings3.add(5.0);
        ratings3.add(3.0);
        Rider rider1 = new Rider("Miguel","Ferreira","937485748","miguelf","password","link",49.4578,76.93284,ratings1);
        Rider rider2 = new Rider("Afonso","Campos","937451448","afonsoc","password","link",49.4455,32.93284,ratings2);
        Rider rider3 = new Rider("Ana","Monteiro","9153726384","anam","password","link",39.4455,12.93284,ratings3);

        rider1.setId(10L);

        List<Rider> orderedRiders = Arrays.asList(rider2,rider3,rider1);

        assertThat(riderService.getRidersAlphabetically()).isEqualTo(orderedRiders);

        verifyFindRidersIsCalledOnce();

    }

    @Test
    void whenGetAllRidersOrderedByMean_thenReturnAllRidersOrderedByMean() {
        List<Double> ratings1 = new ArrayList<>();
        ratings1.add(4.5);
        ratings1.add(4.0);
        List<Double> ratings2 = new ArrayList<>();
        ratings2.add(2.5);
        ratings2.add(3.5);
        List<Double> ratings3 = new ArrayList<>();
        ratings3.add(5.0);
        ratings3.add(3.0);
        Rider rider1 = new Rider("Miguel","Ferreira","937485748","miguelf","password","link",49.4578,76.93284,ratings1);
        Rider rider2 = new Rider("Afonso","Campos","937451448","afonsoc","password","link",49.4455,32.93284,ratings2);
        Rider rider3 = new Rider("Ana","Monteiro","9153726384","anam","password","link",39.4455,12.93284,ratings3);

        rider1.setId(10L);
        
        List<Rider> orderedRiders = Arrays.asList(rider2,rider3,rider1);

        assertThat(riderService.getRidersByRating()).isEqualTo(orderedRiders);

        verifyFindRidersIsCalledOnce();

    }



    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(riderRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
    }

    private void verifyFindRidersIsCalledOnce() {
        Mockito.verify(riderRepository, VerificationModeFactory.times(1)).findAll();
    }

    
}
