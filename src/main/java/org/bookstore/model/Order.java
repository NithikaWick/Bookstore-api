package org.bookstore.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Order {
    private int id;
    private int customerId;
    private Map<Integer, Integer> items; // Map of bookId -> quantity
    private double total;
    private String orderDate;

    // Static date formatter
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // Constructors
    public Order() {
        setCurrentDate(); // Sets current time by default
    }

    // Helper method to set current date
    private void setCurrentDate() {
        this.orderDate = DATE_FORMAT.format(new Date());
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    // For compatibility with code that uses Date
    public void setOrderDate(Date date) {
        if (date != null) {
            this.orderDate = DATE_FORMAT.format(date);
        } else {
            this.orderDate = null;
        }
    }
}