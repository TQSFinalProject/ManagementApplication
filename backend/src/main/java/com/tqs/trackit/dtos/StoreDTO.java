package com.tqs.trackit.dtos;

import com.tqs.trackit.model.Store;

public class StoreDTO {
    private long id;
    private String storeName;
    private Double shippingTax;
    private String storeAddress;
    private Double storeLat;
    private Double storeLong;
    private String username;
    private String password;

    public static StoreDTO fromStoreEntity(Store store){
        return new StoreDTO(store.getStoreName(), store.getShippingTax(),store.getStoreAddress(),store.getStoreLat(),store.getStoreLong(),store.getUser().getUsername(),store.getUser().getPassword(),store.getId());
    }
    public Store toStoreEntity(){
        return new Store(getStoreName(),getShippingTax(),getStoreAddress(),getStoreLat(),getStoreLong(),getUsername(),getPassword());
    }

    public StoreDTO() {
    }

    public StoreDTO(String storeName, Double shippingTax, String storeAddress, Double storeLat, Double storeLong, String username, String password, long id) {
        this.storeName = storeName;
        this.shippingTax = shippingTax;
        this.storeAddress = storeAddress;
        this.storeLat = storeLat;
        this.storeLong = storeLong;
        this.username = username;
        this.password = password;
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
}
