package com.tqs.trackit.dtos;

import java.util.ArrayList;

import com.tqs.trackit.model.Rider;

public class RiderCreationDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private String username;
    private String password;
    private String riderPhoto;

    public Rider toRiderEntity() {
        return new Rider(firstName, lastName, phone, username, password, riderPhoto, 0.0, 0.0, new ArrayList<>());
    }

    public RiderCreationDTO(String firstName, String lastName, String phone, String username, String password, String riderPhoto) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.riderPhoto = riderPhoto;
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
}
