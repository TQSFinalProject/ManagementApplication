package com.tqs.trackit.dtos;

import com.tqs.trackit.model.Store;

public class StoreDTO {
    private long id;
    private String storeName;
    private Double shippingTax;
    private String storeAddress;
    private Double storeLat;
    private Double storeLong;

    public static StoreDTO fromStoreEntity(Store store){
        return new StoreDTO(store.getStoreName(), store.getShippingTax(),store.getStoreAddress(),store.getStoreLat(),store.getStoreLong(),store.getId());
    }
    public Store toStoreEntity(){
        return new Store(getStoreName(),getShippingTax(),getStoreAddress(),getStoreLat(),getStoreLong(),getId());
    }

    public StoreDTO() {
    }

    public StoreDTO(String storeName, Double shippingTax, String storeAddress, Double storeLat, Double storeLong, long id) {
        this.storeName = storeName;
        this.shippingTax = shippingTax;
        this.storeAddress = storeAddress;
        this.storeLat = storeLat;
        this.storeLong = storeLong;
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
}
