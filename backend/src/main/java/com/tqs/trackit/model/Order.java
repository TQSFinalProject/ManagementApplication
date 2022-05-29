package com.tqs.trackit.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order") 
public class Order {
    @Id //The ID will be auto generated
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "delivery_address", nullable = false)
    private String delivery_address;

    @Column(name = "estimated_delivery_time", nullable = false)
    private LocalDateTime estimated_delivery_time;

    @Column(name = "submited_time", nullable = false)
    private LocalDateTime submited_time;

    @Column(name = "rider_id", nullable = false)
    private Long rider_id;

    @Column(name = "store_id", nullable = false)
    private Long store_id;

    @Column(name = "details", nullable = false)
    private String details;

    @Column(name = "phone", nullable = false)
    private Long phone;


    public Order() {
    }


    public Order(long id, String status, String delivery_address, LocalDateTime estimated_delivery_time, LocalDateTime submited_time, Long rider_id, Long store_id, String details, Long phone) {
        this.id = id;
        this.status = status;
        this.delivery_address = delivery_address;
        this.estimated_delivery_time = estimated_delivery_time;
        this.submited_time = submited_time;
        this.rider_id = rider_id;
        this.store_id = store_id;
        this.details = details;
        this.phone = phone;
    }


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelivery_address() {
        return this.delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    public LocalDateTime getEstimated_delivery_time() {
        return this.estimated_delivery_time;
    }

    public void setEstimated_delivery_time(LocalDateTime estimated_delivery_time) {
        this.estimated_delivery_time = estimated_delivery_time;
    }

    public LocalDateTime getSubmited_time() {
        return this.submited_time;
    }

    public void setSubmited_time(LocalDateTime submited_time) {
        this.submited_time = submited_time;
    }

    public Long getRider_id() {
        return this.rider_id;
    }

    public void setRider_id(Long rider_id) {
        this.rider_id = rider_id;
    }

    public Long getStore_id() {
        return this.store_id;
    }

    public void setStore_id(Long store_id) {
        this.store_id = store_id;
    }

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Long getPhone() {
        return this.phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", status='" + getStatus() + "'" +
            ", delivery_address='" + getDelivery_address() + "'" +
            ", estimated_delivery_time='" + getEstimated_delivery_time() + "'" +
            ", submited_time='" + getSubmited_time() + "'" +
            ", rider_id='" + getRider_id() + "'" +
            ", store_id='" + getStore_id() + "'" +
            ", details='" + getDetails() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
