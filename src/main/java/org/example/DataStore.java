package org.example;

import org.example.model.*;
import java.util.HashMap;
import java.util.Map;

public class DataStore {
    private static DataStore instance = null;
    private Map<Integer, Book> books;
    private Map<Integer, Author> authors;
    private Map<Integer, Customer> customers;
    private Map<Integer, Cart> carts;
    private Map<Integer, Order> orders;
    private int bookIdCounter;
    private int authorIdCounter;
    private int customerIdCounter;
    private int orderIdCounter;

    // Singleton pattern
    private DataStore() {
        books = new HashMap<>();
        authors = new HashMap<>();
        customers = new HashMap<>();
        carts = new HashMap<>();
        orders = new HashMap<>();
        bookIdCounter = 1;
        authorIdCounter = 1;
        customerIdCounter = 1;
        orderIdCounter = 1;
        initializeSampleData();
    }

    public static synchronized DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    private void initializeSampleData() {
        // Sample Author
        Author author = new Author(getNextAuthorId(), "J.R.R. Tolkien", "Author of The Lord of the Rings");
        authors.put(author.getId(), author);

        // Sample Book
        Book book = new Book(getNextBookId(), "The Lord of the Rings", author.getId(), "978-0-618-05326-7", 1954, 20.99, 100);
        books.put(book.getId(), book);

        // Sample Customer
        Customer customer = new Customer(getNextCustomerId(), "John Doe", "john@example.com", "password123");
        customers.put(customer.getId(), customer);

        // Sample Cart
        Cart cart = new Cart(customer.getId());
        cart.getItems().put(book.getId(), 2); // 2 copies of the book
        carts.put(customer.getId(), cart);
    }

    // Book methods
    public Map<Integer, Book> getBooks() { return books; }
    public int getNextBookId() { return bookIdCounter++; }

    // Author methods
    public Map<Integer, Author> getAuthors() { return authors; }
    public int getNextAuthorId() { return authorIdCounter++; }

    // Customer methods
    public Map<Integer, Customer> getCustomers() { return customers; }
    public int getNextCustomerId() { return customerIdCounter++; }

    // Cart methods
    public Map<Integer, Cart> getCarts() { return carts; }

    // Order methods
    public Map<Integer, Order> getOrders() { return orders; }
    public int getNextOrderId() { return orderIdCounter++; }
}