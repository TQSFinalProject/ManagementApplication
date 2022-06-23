package com.tqs.trackit.unit;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tqs.trackit.model.Rider;

import static org.assertj.core.api.Assertions.assertThat;

public class RiderTest {
    private Rider rider;

    public RiderTest() {}

    @BeforeEach
    public void setUp() {
        List<Double> ratings = new ArrayList<>();
        ratings.add(4.5);
        ratings.add(4.0);
        rider = new Rider("Joana","Ferreira","937485748","joanaf","password","link",49.4578,76.93284,ratings);
    }

    @Test
    public void testMeanCalculation() {
        assertThat(rider.ratingMean()).isEqualTo(4.25);
    }
}

