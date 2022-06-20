package com.tqs.trackit.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "store") 
public class Store {
    @Id //The ID will be auto generated
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Column(name = "shipping_tax", nullable = false)
    private Double shippingTax;

    @Column(name = "store_address", nullable = false)
    private String storeAddress;

    @Column(name = "password", nullable = false)
    private String password;

    public Store() {
    }

    public Store(String storeName, Double shippingTax, String storeAddress, String password) {
        BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
        this.storeName = storeName;
        this.shippingTax = shippingTax;
        this.storeAddress = storeAddress;
        this.password = bcryptEncoder.encode(password);
    }

    public Store(String storeName, Double shippingTax, String storeAddress, String password, Long id) {
        this(storeName,shippingTax,storeAddress,password);
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Store)) {
            return false;
        }
        Store store = (Store) o;
        return id == store.id && Objects.equals(storeName, store.storeName) && Objects.equals(shippingTax, store.shippingTax) && Objects.equals(storeAddress, store.storeAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, storeName, shippingTax, storeAddress, password);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", storeName='" + getStoreName() + "'" +
            ", shippingTax='" + getShippingTax() + "'" +
            ", storeAddress='" + getStoreAddress() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }

}
