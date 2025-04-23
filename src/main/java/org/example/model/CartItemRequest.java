package org.example.model;

public class CartItemRequest {
    private int bookId;
    private int quantity;

    // Constructors
    public CartItemRequest() {}

    public CartItemRequest(int bookId, int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}