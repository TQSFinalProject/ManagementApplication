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
    private String orderStatus;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @Column(name = "estimated_delivery_time")
    private LocalDateTime estimatedDeliveryTime;

    @Column(name = "submited_time", nullable = false)
    private LocalDateTime submitedTime;

    @Column(name = "delivery_time")
    private LocalDateTime deliveryTime;

    @Column(name = "rider_id")
    private Long riderId;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "order_details")
    private String orderDetails;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name="rating")
    private Double rating;

    public Order() {
    }


    public Order(String orderStatus, String deliveryAddress, LocalDateTime estimatedDeliveryTime, LocalDateTime submitedTime, LocalDateTime deliveryTime, Long riderId, Long storeId, String orderDetails, String phone, Double rating) {
        this.orderStatus = orderStatus;
        this.deliveryAddress = deliveryAddress;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.submitedTime = submitedTime;
        this.deliveryTime = deliveryTime;
        this.riderId = riderId;
        this.storeId = storeId;
        this.orderDetails = orderDetails;
        this.phone = phone;
        this.rating = rating;
    }



    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDeliveryAddress() {
        return this.deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public LocalDateTime getEstimatedDeliveryTime() {
        return this.estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(LocalDateTime estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public LocalDateTime getSubmitedTime() {
        return this.submitedTime;
    }

    public void setSubmitedTime(LocalDateTime submitedTime) {
        this.submitedTime = submitedTime;
    }

    public LocalDateTime getDeliveryTime() {
        return this.deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Long getRiderId() {
        return this.riderId;
    }

    public void setRiderId(Long riderId) {
        this.riderId = riderId;
    }

    public Long getStoreId() {
        return this.storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getOrderDetails() {
        return this.orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
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
        return id == order.id && Objects.equals(orderStatus, order.orderStatus) && Objects.equals(deliveryAddress, order.deliveryAddress) && Objects.equals(estimatedDeliveryTime, order.estimatedDeliveryTime) && Objects.equals(submitedTime, order.submitedTime) && Objects.equals(deliveryTime, order.deliveryTime) && Objects.equals(riderId, order.riderId) && Objects.equals(storeId, order.storeId) && Objects.equals(orderDetails, order.orderDetails) && Objects.equals(phone, order.phone) && Objects.equals(rating, order.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderStatus, deliveryAddress, estimatedDeliveryTime, submitedTime, deliveryTime, riderId, storeId, orderDetails, phone, rating);
    }



    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", orderStatus='" + getOrderStatus() + "'" +
            ", deliveryAddress='" + getDeliveryAddress() + "'" +
            ", estimatedDeliveryTime='" + getEstimatedDeliveryTime() + "'" +
            ", submitedTime='" + getSubmitedTime() + "'" +
            ", deliveryTime='" + getDeliveryTime() + "'" +
            ", riderId='" + getRiderId() + "'" +
            ", storeId='" + getStoreId() + "'" +
            ", orderDetails='" + getOrderDetails() + "'" +
            ", phone='" + getPhone() + "'" +
            ", rating='" + getRating() + "'" +
            "}";
    }


}