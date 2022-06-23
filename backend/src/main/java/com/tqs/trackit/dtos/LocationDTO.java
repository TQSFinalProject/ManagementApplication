package com.tqs.trackit.dtos;

public class LocationDTO {
    private Double longitude;
    private Double latitude;

    public LocationDTO(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Double getLatitude() {
        return this.latitude;
    }
}
