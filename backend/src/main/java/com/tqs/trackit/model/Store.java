package com.tqs.trackit.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "store") 
public class Store {
    @Id //The ID will be auto generated
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "shipping_tax", nullable = false)
    private Double shipping_tax;

    @Column(name = "store_address", nullable = false)
    private String store_address;


    public Store() {
    }


    public Store(long id, Double shipping_tax, String store_address) {
        this.id = id;
        this.shipping_tax = shipping_tax;
        this.store_address = store_address;
    }


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getShipping_tax() {
        return this.shipping_tax;
    }

    public void setShipping_tax(Double shipping_tax) {
        this.shipping_tax = shipping_tax;
    }

    public String getStore_address() {
        return this.store_address;
    }

    public void setStore_address(String store_address) {
        this.store_address = store_address;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Store)) {
            return false;
        }
        Store store = (Store) o;
        return id == store.id && Objects.equals(shipping_tax, store.shipping_tax) && Objects.equals(store_address, store.store_address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shipping_tax, store_address);
    }



    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", shipping_tax='" + getShipping_tax() + "'" +
            ", store_address='" + getStore_address() + "'" +
            "}";
    }

    
}
