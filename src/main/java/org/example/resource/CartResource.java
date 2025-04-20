package org.example.resource;

import org.example.DataStore;
import org.example.exception.BookNotFoundException;
import org.example.exception.CustomerNotFoundException;
import org.example.exception.InsufficientStockException;
import org.example.model.Book;
import org.example.model.Cart;
import org.example.model.CartItemRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customers/{customerId}/cart")
public class CartResource {
    private final DataStore dataStore = DataStore.getInstance();

    // GET /customers/{customerId}/cart - Retrieve a customer's cart
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Cart getCart(@PathParam("customerId") int customerId) {
        // Check if customer exists
        if (!dataStore.getCustomers().containsKey(customerId)) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found");
        }
        Cart cart = dataStore.getCarts().get(customerId);
        if (cart == null) {
            cart = new Cart(customerId);
            dataStore.getCarts().put(customerId, cart);
        }
        return cart;
    }

    // POST /customers/{customerId}/cart - Add a book to the cart
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addToCart(@PathParam("customerId") int customerId, CartItemRequest request) {
        // Validate customer
        if (!dataStore.getCustomers().containsKey(customerId)) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found");
        }

        // Validate book
        Book book = dataStore.getBooks().get(request.getBookId());
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + request.getBookId() + " not found");
        }

        // Check stock
        int currentStock = book.getStock();
        int requestedQuantity = request.getQuantity();
        if (requestedQuantity <= 0) {
            throw new InsufficientStockException("Quantity must be greater than 0");
        }
        if (currentStock < requestedQuantity) {
            throw new InsufficientStockException("Insufficient stock for book ID " + request.getBookId() + ". Available: " + currentStock);
        }

        // Add to cart
        Cart cart = dataStore.getCarts().get(customerId);
        if (cart == null) {
            cart = new Cart(customerId);
            dataStore.getCarts().put(customerId, cart);
        }
        cart.getItems().merge(request.getBookId(), requestedQuantity, Integer::sum);
        return Response.ok(cart).build();
    }
}