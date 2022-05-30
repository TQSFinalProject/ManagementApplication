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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String first_name;

    @Column(name = "last_name", nullable = false)
    private String last_name;

    @Column(name = "phone", nullable = false)
    private Long phone;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "ratings", nullable = false)
    @ElementCollection
    private List<Double> ratings;


    public Rider() {
    }



    public Rider(long id, String first_name, String last_name, Long phone, String username, String password, Double latitude, Double longitude, List<Double> ratings) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ratings = ratings;
    }


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Long getPhone() {
        return this.phone;
    }

    public void setPhone(Long phone) {
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
        return id == rider.id && Objects.equals(first_name, rider.first_name) && Objects.equals(last_name, rider.last_name) && Objects.equals(phone, rider.phone) && Objects.equals(username, rider.username) && Objects.equals(password, rider.password) && Objects.equals(latitude, rider.latitude) && Objects.equals(longitude, rider.longitude) && Objects.equals(ratings, rider.ratings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name, phone, username, password, latitude, longitude, ratings);
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", first_name='" + getFirst_name() + "'" +
            ", last_name='" + getLast_name() + "'" +
            ", phone='" + getPhone() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", ratings='" + getRatings() + "'" +
            "}";
    }
    
}
