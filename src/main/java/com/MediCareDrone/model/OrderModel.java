package com.MediCareDrone.model;

import java.math.BigDecimal;

public class OrderModel {
    private int orderId;
    private int userId;
    private int droneId;
    private int quantity;
    private BigDecimal totalPrice;

    // Default constructor
    public OrderModel() {}

    // Parameterized constructor
    public OrderModel(int orderId, int userId, int droneId, int quantity, BigDecimal totalPrice) {
        this.orderId = orderId;
        this.userId = userId;
        this.droneId = droneId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDroneId() {
        return droneId;
    }

    public void setDroneId(int droneId) {
        this.droneId = droneId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

}
