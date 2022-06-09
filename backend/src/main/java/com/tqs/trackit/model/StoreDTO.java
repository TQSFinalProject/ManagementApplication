package com.tqs.trackit.model;

public class StoreDTO {
    private long id;
    private String storeName;
    private Double shippingTax;
    private String storeAddress;

    public static StoreDTO fromStoreEntity(Store store){
        return new StoreDTO(store.getStoreName(), store.getShippingTax(), store.getStoreAddress(),store.getId());
    }
    public Store toStoreEntity(){
        return new Store(getStoreName(), getShippingTax(),getStoreAddress(), getId());
    }

    public StoreDTO() {
    }

    public StoreDTO(String storeName, Double shippingTax, String storeAddress, long id) {
        this.storeName = storeName;
        this.shippingTax = shippingTax;
        this.storeAddress = storeAddress;
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

}
