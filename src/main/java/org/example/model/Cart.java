package org.example.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private int customerId;
    private Map<Integer, Integer> items; // Map of bookId to quantity

    // Constructor
    public Cart() {
        this.items = new HashMap<>();
    }

    public Cart(int customerId) {
        this.customerId = customerId;
        this.items = new HashMap<>();
    }

    // Getters and Setters
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public Map<Integer, Integer> getItems() { return items; }
    public void setItems(Map<Integer, Integer> items) { this.items = items; }

    @Override
    public String toString() {
        return "Cart{" +
                "customerId=" + customerId +
                ", items=" + items +
                '}';
    }
}