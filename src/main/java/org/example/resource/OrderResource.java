package org.example.resource;

import org.example.DataStore;
import org.example.exception.CustomerNotFoundException;
import org.example.exception.OrderNotFoundException;
import org.example.exception.CartNotFoundException;
import org.example.exception.OutOfStockException;
import org.example.model.Book;
import org.example.model.Cart;
import org.example.model.Order;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/customers/{customerId}/orders")
public class OrderResource {
    private final DataStore dataStore = DataStore.getInstance();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(@PathParam("customerId") int customerId) {
        // Check if customer exists
        if (!dataStore.getCustomers().containsKey(customerId)) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found");
        }

        // Check if cart exists and has items
        Cart cart = dataStore.getCarts().get(customerId);
        if (cart == null) {
            throw new CartNotFoundException("Cart for customer ID " + customerId + " not found");
        }

        if (cart.getItems().isEmpty()) {
            throw new org.example.exception.InvalidInputException("Cannot create order with empty cart");
        }

        // Check stock availability and calculate total
        double total = 0.0;
        Map<Integer, Integer> orderItems = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry : cart.getItems().entrySet()) {
            int bookId = entry.getKey();
            int quantity = entry.getValue();

            Book book = dataStore.getBooks().get(bookId);
            if (book == null) {
                throw new org.example.exception.BookNotFoundException("Book with ID " + bookId + " not found");
            }

            if (book.getStock() < quantity) {
                throw new OutOfStockException("Insufficient stock for book ID " + bookId +
                        ". Requested: " + quantity + ", Available: " + book.getStock());
            }

            // Calculate subtotal for this book
            total += book.getPrice() * quantity;

            // Add to order items
            orderItems.put(bookId, quantity);

            // Update book stock
            book.setStock(book.getStock() - quantity);
        }

        // Create new order
        int orderId = dataStore.getNextOrderId();
        Order order = new Order();
        order.setId(orderId);
        order.setCustomerId(customerId);
        order.setItems(orderItems);
        order.setTotal(total);
        order.setOrderDate(LocalDateTime.now());

        // Add order to data store
        dataStore.getOrders().put(orderId, order);

        // Clear the customer's cart
        cart.getItems().clear();

        return Response.status(Response.Status.CREATED)
                .entity(order)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getOrdersByCustomer(@PathParam("customerId") int customerId) {
        // Check if customer exists
        if (!dataStore.getCustomers().containsKey(customerId)) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found");
        }

        // Filter orders by customer ID
        return dataStore.getOrders().values().stream()
                .filter(order -> order.getCustomerId() == customerId)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Order getOrderById(@PathParam("customerId") int customerId, @PathParam("orderId") int orderId) {
        // Check if customer exists
        if (!dataStore.getCustomers().containsKey(customerId)) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found");
        }

        // Get the order
        Order order = dataStore.getOrders().get(orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order with ID " + orderId + " not found");
        }

        // Check if the order belongs to the customer
        if (order.getCustomerId() != customerId) {
            throw new OrderNotFoundException("Order with ID " + orderId + " not found for customer ID " + customerId);
        }

        return order;
    }
}