package com.tqs.trackit.dtos;

import com.tqs.trackit.model.Store;

public class StorePrivateDTO {
    private long id;
    private String storeName;
    private Double shippingTax;
    private String storeAddress;

    public static StorePrivateDTO fromStoreEntity(Store store){
        return new StorePrivateDTO(store.getId(), store.getStoreName(), store.getShippingTax(), store.getStoreAddress());
    }

    public StorePrivateDTO(long id, String storeName, Double shippingTax, String storeAddress) {
        this.id = id;
        this.storeName = storeName;
        this.shippingTax = shippingTax;
        this.storeAddress = storeAddress;
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
}
