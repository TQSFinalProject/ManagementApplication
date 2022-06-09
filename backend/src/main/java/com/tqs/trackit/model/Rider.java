package com.tqs.trackit.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rider") 
public class Rider {
    @Id //The ID will be auto generated
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "rider_photo", nullable = false)
    private String riderPhoto;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "ratings", nullable = false)
    @ElementCollection
    private List<Double> ratings;


    public Rider() {
    }



    public Rider(String firstName, String lastName, String phone, String username, String password, String riderPhoto, Double latitude, Double longitude, List<Double> ratings) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.riderPhoto = riderPhoto;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ratings = ratings;
    }

    public Rider(String firstName, String lastName, String phone, String username, String password, String riderPhoto, Double latitude, Double longitude, List<Double> ratings,Long id) {
        this(firstName,lastName,phone,username,password,riderPhoto,latitude,longitude,ratings);
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

    

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Rider)) {
            return false;
        }
        Rider rider = (Rider) o;
        return id == rider.id && Objects.equals(firstName, rider.firstName) && Objects.equals(lastName, rider.lastName) && Objects.equals(phone, rider.phone) && Objects.equals(username, rider.username) && Objects.equals(password, rider.password) && Objects.equals(riderPhoto, rider.riderPhoto) && Objects.equals(latitude, rider.latitude) && Objects.equals(longitude, rider.longitude) && Objects.equals(ratings, rider.ratings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, phone, username, password, riderPhoto, latitude, longitude, ratings);
    }

    public Double ratingMean() {
        Double ratingSum = 0.0;
        for(Double r : this.ratings) {
            ratingSum+=r;
        }
        return ratingSum/this.ratings.size();
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", riderPhoto='" + getRiderPhoto() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", ratings='" + getRatings() + "'" +
            "}";
    }

    
    
}
