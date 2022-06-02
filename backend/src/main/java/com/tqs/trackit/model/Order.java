package com.tqs.trackit.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
    @Id // The ID will be auto generated
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "order_status", nullable = false)
    private String order_status;

    @Column(name = "delivery_address", nullable = false)
    private String delivery_address;

    @Column(name = "estimated_delivery_time")
    private LocalDateTime estimated_delivery_time;

    @Column(name = "submited_time", nullable = false)
    private LocalDateTime submited_time;

    @Column(name = "delivery_time")
    private LocalDateTime delivery_time;

    @Column(name = "rider_id")
    private Long rider_id;

    @Column(name = "store_id")
    private Long store_id;

    @Column(name = "order_details")
    private String order_details;

    @Column(name = "phone", nullable = false)
    private Long phone;

    @Column(name="rating")
    private Double rating;

    public Order() {
    }


    public Order(long id, String order_status, String delivery_address, LocalDateTime estimated_delivery_time, LocalDateTime submited_time, LocalDateTime delivery_time, Long rider_id, Long store_id, String order_details, Long phone, Double rating) {
        this.id = id;
        this.order_status = order_status;
        this.delivery_address = delivery_address;
        this.estimated_delivery_time = estimated_delivery_time;
        this.submited_time = submited_time;
        this.delivery_time = delivery_time;
        this.rider_id = rider_id;
        this.store_id = store_id;
        this.order_details = order_details;
        this.phone = phone;
        this.rating = rating;
    }


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrder_status() {
        return this.order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
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

    public LocalDateTime getDelivery_time() {
        return this.delivery_time;
    }

    public void setDelivery_time(LocalDateTime delivery_time) {
        this.delivery_time = delivery_time;
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

    public String getOrder_details() {
        return this.order_details;
    }

    public void setOrder_details(String order_details) {
        this.order_details = order_details;
    }

    public Long getPhone() {
        return this.phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Double getRating() {
        return this.rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Order)) {
            return false;
        }
        Order order = (Order) o;
        return id == order.id && Objects.equals(order_status, order.order_status) && Objects.equals(delivery_address, order.delivery_address) && Objects.equals(estimated_delivery_time, order.estimated_delivery_time) && Objects.equals(submited_time, order.submited_time) && Objects.equals(delivery_time, order.delivery_time) && Objects.equals(rider_id, order.rider_id) && Objects.equals(store_id, order.store_id) && Objects.equals(order_details, order.order_details) && Objects.equals(phone, order.phone) && Objects.equals(rating, order.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order_status, delivery_address, estimated_delivery_time, submited_time, delivery_time, rider_id, store_id, order_details, phone, rating);
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", order_status='" + getOrder_status() + "'" +
            ", delivery_address='" + getDelivery_address() + "'" +
            ", estimated_delivery_time='" + getEstimated_delivery_time() + "'" +
            ", submited_time='" + getSubmited_time() + "'" +
            ", delivery_time='" + getDelivery_time() + "'" +
            ", rider_id='" + getRider_id() + "'" +
            ", store_id='" + getStore_id() + "'" +
            ", order_details='" + getOrder_details() + "'" +
            ", phone='" + getPhone() + "'" +
            ", rating='" + getRating() + "'" +
            "}";
    }


}