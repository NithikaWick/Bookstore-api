package org.bookstore;

import org.bookstore.model.Author;
import org.bookstore.model.Book;
import org.bookstore.model.Cart;
import org.bookstore.model.Customer;
import org.bookstore.model.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DataStore {
    private static final DataStore instance = new DataStore();
    private final Map<Integer, Book> books = new HashMap<>();
    private final Map<Integer, Author> authors = new HashMap<>();
    private final Map<Integer, Customer> customers = new HashMap<>();
    private final Map<Integer, Cart> carts = new HashMap<>();
    private final Map<Integer, Order> orders = new HashMap<>();

    private final AtomicInteger bookIdCounter = new AtomicInteger(1);
    private final AtomicInteger authorIdCounter = new AtomicInteger(1);
    private final AtomicInteger customerIdCounter = new AtomicInteger(1);
    private final AtomicInteger orderIdCounter = new AtomicInteger(1);

    private DataStore() {
//        // Initialize sample authors
//        authors.put(1, new Author(1, "J.R.R. Tolkien", "Author of The Lord of the Rings"));
//        authors.put(2, new Author(2, "George R.R. Martin", "Author of A Song of Ice and Fire"));
//
//        // Initialize sample books
//        books.put(1, new Book(1, "The Lord of the Rings", 1, "978-0-618-05326-7", 1954, 20.99, 100));
//
//        // Initialize sample customers
//        customers.put(1, new Customer(1, "John Doe", "john@bookstore.com", "password123"));
//        customers.put(2, new Customer(2, "Jane Smith", "jane@bookstore.com", "pass456"));
//
//        // Initialize carts for customers
//        carts.put(1, new Cart(1));
//        carts.put(2, new Cart(2));
    }

    public static DataStore getInstance() {
        return instance;
    }

    public Map<Integer, Book> getBooks() {
        return books;
    }

    public Map<Integer, Author> getAuthors() {
        return authors;
    }

    public Map<Integer, Customer> getCustomers() {
        return customers;
    }

    public Map<Integer, Cart> getCarts() {
        return carts;
    }

    public Map<Integer, Order> getOrders() {
        return orders;
    }

    public int getNextBookId() {
        return bookIdCounter.getAndIncrement();
    }

    public int getNextAuthorId() {
        return authorIdCounter.getAndIncrement();
    }

    public int getNextCustomerId() {
        return customerIdCounter.getAndIncrement();
    }

    public int getNextOrderId() {
        return orderIdCounter.getAndIncrement();
    }
}