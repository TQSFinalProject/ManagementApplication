package com.tqs.trackit.dtos;

import java.time.LocalDateTime;

import com.tqs.trackit.model.Order;

public class OrderCreationDTO {
    private String orderStatus;
    private String deliveryAddress;
    private Double deliveryLat;
    private Double deliveryLong;
    private LocalDateTime submitedTime;
    private String orderDetails;
    private String phone;

    public OrderCreationDTO(String orderStatus, String deliveryAddress, Double deliveryLat, Double deliveryLong, LocalDateTime submitedTime, String orderDetails, String phone) {
        this.orderStatus = orderStatus;
        this.deliveryAddress = deliveryAddress;
        this.deliveryLat = deliveryLat;
        this.deliveryLong = deliveryLong;
        this.submitedTime = submitedTime;
        this.orderDetails = orderDetails;
        this.phone = phone;
    }

    public Order toOrderEntity() {
        return new Order(orderStatus, deliveryAddress, deliveryLat, deliveryLong,null, submitedTime, null, null, null, orderDetails, phone, null);
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public String getDeliveryAddress() {
        return this.deliveryAddress;
    }

    public Double getDeliveryLat() {
        return this.deliveryLat;
    }

    public Double getDeliveryLong() {
        return this.deliveryLong;
    }

    public LocalDateTime getSubmitedTime() {
        return this.submitedTime;
    }

    public String getOrderDetails() {
        return this.orderDetails;
    }

    public String getPhone() {
        return this.phone;
    }

    @Override
    public String toString() {
        return "{" +
            " orderStatus='" + getOrderStatus() + "'" +
            ", deliveryAddress='" + getDeliveryAddress() + "'" +
            ", deliveryLat='" + getDeliveryLat() + "'" +
            ", deliveryLong='" + getDeliveryLong() + "'" +
            ", submitedTime='" + getSubmitedTime() + "'" +
            ", orderDetails='" + getOrderDetails() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }

}
