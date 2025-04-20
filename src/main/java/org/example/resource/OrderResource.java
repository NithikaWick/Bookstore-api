package org.example.resource;

import org.example.DataStore;
import org.example.exception.CustomerNotFoundException;
import org.example.exception.InsufficientStockException;
import org.example.model.Book;
import org.example.model.Cart;
import org.example.model.Order;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/orders")
public class OrderResource {
    private final DataStore dataStore = DataStore.getInstance();

    // POST /orders - Create a new order from a customer's cart
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(@QueryParam("customerId") int customerId) {
        // Validate customer
        if (!dataStore.getCustomers().containsKey(customerId)) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found");
        }

        // Get the customer's cart
        Cart cart = dataStore.getCarts().get(customerId);
        if (cart == null || cart.getItems().isEmpty()) {
            throw new WebApplicationException("Cart is empty for customer ID " + customerId, Response.Status.BAD_REQUEST);
        }

        // Validate stock and calculate total price
        double totalPrice = 0.0;
        Map<Integer, Integer> orderItems = new HashMap<>(cart.getItems());
        for (Map.Entry<Integer, Integer> entry : orderItems.entrySet()) {
            int bookId = entry.getKey();
            int quantity = entry.getValue();
            Book book = dataStore.getBooks().get(bookId);
            if (book == null) {
                throw new WebApplicationException("Book with ID " + bookId + " not found in cart", Response.Status.BAD_REQUEST);
            }
            if (book.getStock() < quantity) {
                throw new InsufficientStockException("Insufficient stock for book ID " + bookId + ". Available: " + book.getStock());
            }
            totalPrice += book.getPrice() * quantity;
        }

        // Update stock
        for (Map.Entry<Integer, Integer> entry : orderItems.entrySet()) {
            int bookId = entry.getKey();
            int quantity = entry.getValue();
            Book book = dataStore.getBooks().get(bookId);
            book.setStock(book.getStock() - quantity);
        }

        // Create the order
        int orderId = dataStore.getNextOrderId();
        Order order = new Order(orderId, customerId, orderItems, totalPrice);
        dataStore.getOrders().put(orderId, order);

        // Clear the cart
        cart.getItems().clear();

        return Response.status(Response.Status.CREATED)
                .entity(order)
                .build();
    }
}