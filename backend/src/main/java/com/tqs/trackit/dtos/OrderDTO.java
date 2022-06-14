package com.tqs.trackit.dtos;

import com.tqs.trackit.model.Order;

import java.time.LocalDateTime;

public class OrderDTO {
    private long id;
    private String orderStatus;
    private String deliveryAddress;
    private LocalDateTime estimatedDeliveryTime;
    private LocalDateTime submitedTime;
    private LocalDateTime deliveryTime;
    private Long riderId;
    private Long storeId;
    private String orderDetails;
    private String phone;
    private Double rating;

    public static OrderDTO fromOrderEntity(Order order){
        return new OrderDTO(order.getOrderStatus(),order.getDeliveryAddress(),order.getEstimatedDeliveryTime(),order.getSubmitedTime(),order.getDeliveryTime(),order.getRiderId(),order.getStoreId(),order.getOrderDetails(),order.getPhone(),order.getRating(),order.getId());
    }
    public Order toOrderEntity(){
        return new Order(getOrderStatus(),getDeliveryAddress(),getEstimatedDeliveryTime(),getSubmitedTime(),getDeliveryTime(),getRiderId(),getStoreId(),getOrderDetails(),getPhone(),getRating(),getId());
    }

    public OrderDTO() {
    }

    public OrderDTO(String orderStatus, String deliveryAddress, LocalDateTime estimatedDeliveryTime, LocalDateTime submitedTime, LocalDateTime deliveryTime, Long riderId, Long storeId, String orderDetails, String phone, Double rating, Long id) {
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
        this.id = id;
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

}
