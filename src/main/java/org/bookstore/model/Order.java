package org.bookstore.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private int id;
    private int customerId;
    private Map<Integer, Integer> items = new HashMap<>(); // bookId -> quantity
    private double total;
    private LocalDateTime orderDate;

    // Constructors
    public Order() {}

    public Order(int id, int customerId, Map<Integer, Integer> items, double total, LocalDateTime orderDate) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.total = total;
        this.orderDate = orderDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Map<Integer, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Integer, Integer> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}