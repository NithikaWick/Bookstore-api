package org.bookstore.resource;

import org.bookstore.DataStore;
import org.bookstore.exception.BookNotFoundException;
import org.bookstore.exception.CartNotFoundException;
import org.bookstore.exception.CustomerNotFoundException;
import org.bookstore.exception.InvalidInputException;
import org.bookstore.exception.OutOfStockException;
import org.bookstore.model.Book;
import org.bookstore.model.Cart;
import org.bookstore.model.CartItemRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customers/{customerId}/cart")
public class CartResource {
    private final DataStore dataStore = DataStore.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Cart getCart(@PathParam("customerId") int customerId) {
        if (!dataStore.getCustomers().containsKey(customerId)) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found");
        }
        Cart cart = dataStore.getCarts().get(customerId);
        if (cart == null) {
            throw new CartNotFoundException("Cart for customer ID " + customerId + " not found");
        }
        return cart;
    }

    @POST
    @Path("/items")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addToCart(@PathParam("customerId") int customerId, CartItemRequest request) {
        if (!dataStore.getCustomers().containsKey(customerId)) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found");
        }
        Cart cart = dataStore.getCarts().get(customerId);
        if (cart == null) {
            throw new CartNotFoundException("Cart for customer ID " + customerId + " not found");
        }

        int bookId = request.getBookId();
        int quantity = request.getQuantity();
        Book book = dataStore.getBooks().get(bookId);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + bookId + " not found");
        }
        if (quantity <= 0) {
            throw new InvalidInputException("Quantity must be greater than 0");
        }
        int currentQuantity = cart.getItems().getOrDefault(bookId, 0);
        int newQuantity = currentQuantity + quantity;
        if (book.getStock() < newQuantity) {
            throw new OutOfStockException("Insufficient stock for book ID " + bookId + ". Available: " + book.getStock());
        }
        cart.getItems().put(bookId, newQuantity);
        return Response.ok(cart).build();
    }

    @PUT
    @Path("/items/{bookId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCartItem(@PathParam("customerId") int customerId, @PathParam("bookId") int bookId, CartItemRequest request) {
        if (!dataStore.getCustomers().containsKey(customerId)) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found");
        }
        Cart cart = dataStore.getCarts().get(customerId);
        if (cart == null) {
            throw new CartNotFoundException("Cart for customer ID " + customerId + " not found");
        }
        Book book = dataStore.getBooks().get(bookId);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + bookId + " not found");
        }
        int quantity = request.getQuantity();
        if (quantity <= 0) {
            throw new InvalidInputException("Quantity must be greater than 0");
        }
        if (book.getStock() < quantity) {
            throw new OutOfStockException("Insufficient stock for book ID " + bookId + ". Available: " + book.getStock());
        }
        cart.getItems().put(bookId, quantity);
        return Response.ok(cart).build();
    }

    @DELETE
    @Path("/items/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeCartItem(@PathParam("customerId") int customerId, @PathParam("bookId") int bookId) {
        if (!dataStore.getCustomers().containsKey(customerId)) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found");
        }
        Cart cart = dataStore.getCarts().get(customerId);
        if (cart == null) {
            throw new CartNotFoundException("Cart for customer ID " + customerId + " not found");
        }
        if (!cart.getItems().containsKey(bookId)) {
            throw new BookNotFoundException("Book with ID " + bookId + " not found in cart");
        }
        cart.getItems().remove(bookId);
        return Response.ok(cart).build();
    }
}