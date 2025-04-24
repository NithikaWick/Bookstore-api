package org.bookstore.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private int customerId;
    private Map<Integer, Integer> items = new HashMap<>(); // bookId -> quantity

    // Constructors
    public Cart() {}

    public Cart(int customerId) {
        this.customerId = customerId;
    }

    // Getters and Setters
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
}