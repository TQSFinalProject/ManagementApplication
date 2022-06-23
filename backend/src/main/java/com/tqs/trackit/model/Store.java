package com.tqs.trackit.model;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "store") 
public class Store {
    @Id //The ID will be auto generated
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "store_name", nullable = false, unique = true)
    private String storeName;

    @Column(name = "shipping_tax", nullable = false)
    private Double shippingTax;

    @Column(name = "store_address", nullable = false)
    private String storeAddress;

    @Column(name = "store_lat", nullable = false)
    private Double storeLat;

    @Column(name = "store_long", nullable = false)
    private Double storeLong;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userid")
    private User user;

    public Store() {
    }

    public Store(String storeName, Double shippingTax, String storeAddress, Double storeLat, Double storeLong, String username, String password) {
        this.storeName = storeName;
        this.shippingTax = shippingTax;
        this.storeAddress = storeAddress;
        this.storeLat = storeLat;
        this.storeLong = storeLong;
        this.user = new User(username, password);
    }

    public Store(String storeName, Double shippingTax, String storeAddress, Double storeLat, Double storeLong, String username, String password, Long id) {
        this(storeName,shippingTax,storeAddress,storeLat,storeLong,username,password);
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Double getShippingTax() {
        return this.shippingTax;
    }

    public void setShippingTax(Double shippingTax) {
        this.shippingTax = shippingTax;
    }

    public String getStoreAddress() {
        return this.storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public Double getStoreLat() {
        return this.storeLat;
    }

    public void setStoreLat(Double storeLat) {
        this.storeLat = storeLat;
    }

    public Double getStoreLong() {
        return this.storeLong;
    }

    public void setStoreLong(Double storeLong) {
        this.storeLong = storeLong;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Store)) {
            return false;
        }
        Store store = (Store) o;
        return id == store.id && Objects.equals(storeName, store.storeName) && Objects.equals(shippingTax, store.shippingTax) && Objects.equals(storeAddress, store.storeAddress) && Objects.equals(storeLat, store.storeLat) && Objects.equals(storeLong, store.storeLong) && Objects.equals(user, store.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, storeName, shippingTax, storeAddress, storeLat, storeLong);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", storeName='" + getStoreName() + "'" +
            ", shippingTax='" + getShippingTax() + "'" +
            ", storeAddress='" + getStoreAddress() + "'" +
            ", storeLat='" + getStoreLat() + "'" +
            ", storeLong='" + getStoreLong() + "'" +
            ", user='" + getUser() + "'" +
            "}";
    }
}
