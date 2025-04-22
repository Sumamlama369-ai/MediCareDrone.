package com.MediCareDrone.model;

import java.math.BigDecimal;

public class ProductModel {
    private int droneId;
    private String droneName;
    private String payloadCapacity;
    private int maxRangeKm;
    private BigDecimal price;
    private String warrantyPeriod;

    // Default constructor
    public ProductModel() {}

    // Parameterized constructor
    public ProductModel(int droneId, String droneName, String payloadCapacity, int maxRangeKm,
                          BigDecimal price, String warrantyPeriod) {
        this.droneId = droneId;
        this.droneName = droneName;
        this.payloadCapacity = payloadCapacity;
        this.maxRangeKm = maxRangeKm;
        this.price = price;
        this.warrantyPeriod = warrantyPeriod;
    }
    
 // Constructor without droneId for add or update
    public ProductModel(String droneName, String payloadCapacity, int maxRangeKm, 
                        BigDecimal price, String warrantyPeriod) {
        this.droneName = droneName;
        this.payloadCapacity = payloadCapacity;
        this.maxRangeKm = maxRangeKm;
        this.price = price;
        this.warrantyPeriod = warrantyPeriod;
    }


    // Getters and Setters
    public int getDroneId() {
        return droneId;
    }

    public void setDroneId(int droneId) {
        this.droneId = droneId;
    }

    public String getDroneName() {
        return droneName;
    }

    public void setDroneName(String droneName) {
        this.droneName = droneName;
    }

    public String getPayloadCapacity() {
        return payloadCapacity;
    }

    public void setPayloadCapacity(String payloadCapacity) {
        this.payloadCapacity = payloadCapacity;
    }

    public int getMaxRangeKm() {
        return maxRangeKm;
    }

    public void setMaxRangeKm(int maxRangeKm) {
        this.maxRangeKm = maxRangeKm;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

   
}
