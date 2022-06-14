package com.tqs.trackit.dtos;

import com.tqs.trackit.model.Rider;

import java.util.List;

public class RiderDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String username;
    private String password;
    private String riderPhoto;
    private Double latitude;
    private Double longitude;
    private List<Double> ratings;

    public static RiderDTO fromRiderEntity(Rider rider){
        return new RiderDTO(rider.getFirstName(), rider.getLastName(), rider.getPhone(), rider.getUsername(), rider.getPassword(), rider.getRiderPhoto(), rider.getLatitude(), rider.getLongitude(), rider.getRatings(), rider.getId());
    }
    public Rider toRiderEntity(){
        return new Rider(getFirstName(), getLastName(), getPhone(),getUsername(),getPassword(),getRiderPhoto(),getLatitude(),getLongitude(),getRatings(), getId());
    }

    public RiderDTO() {
    }

    public RiderDTO(String firstName, String lastName, String phone, String username, String password, String riderPhoto, Double latitude, Double longitude, List<Double> ratings,Long id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.riderPhoto = riderPhoto;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ratings = ratings;
        this.id = id;
    }


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRiderPhoto() {
        return this.riderPhoto;
    }

    public void setRiderPhoto(String riderPhoto) {
        this.riderPhoto = riderPhoto;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<Double> getRatings() {
        return this.ratings;
    }

    public void setRatings(List<Double> ratings) {
        this.ratings = ratings;
    }

    
}
