package com.tqs.trackit.dtos;

import com.tqs.trackit.model.Store;

public class StoreRegistrationDTO {
    private String storeName;
    private Double shippingTax;
    private String storeAddress;
    private String password;

    public Store toStoreEntity(){
        return new Store(getStoreName(), getShippingTax(), getStoreAddress(), getPassword());
    }

    public StoreRegistrationDTO(String storeName, Double shippingTax, String storeAddress, String password) {
        this.storeName = storeName;
        this.shippingTax = shippingTax;
        this.storeAddress = storeAddress;
        this.password = password;
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

}
