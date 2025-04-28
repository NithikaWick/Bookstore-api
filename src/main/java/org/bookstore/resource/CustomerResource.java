package org.bookstore.resource;

import org.bookstore.DataStore;
import org.bookstore.exception.CustomerNotFoundException;
import org.bookstore.exception.InvalidInputException;
import org.bookstore.model.Customer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/customers")
public class CustomerResource {
    private final DataStore dataStore = DataStore.getInstance();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(Customer customer) {
        // Basic validation
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new org.bookstore.exception.InvalidInputException("Customer name cannot be empty");
        }

        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty() || !customer.getEmail().contains("@")) {
            throw new org.bookstore.exception.InvalidInputException("Invalid email format");
        }

        if (customer.getPassword() == null || customer.getPassword().trim().isEmpty()) {
            throw new org.bookstore.exception.InvalidInputException("Password cannot be empty");
        }
        validateCustomerData(customer);

        int id = dataStore.getNextCustomerId();
        customer.setId(id);
        dataStore.getCustomers().put(id, customer);

        // Create an empty cart for the new customer
        dataStore.getCarts().put(id, new org.bookstore.model.Cart(id));

        return Response.status(Response.Status.CREATED)
                .entity(customer)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getAllCustomers() {
        return dataStore.getCustomers().values().stream().collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer getCustomerById(@PathParam("id") int id) {
        Customer customer = dataStore.getCustomers().get(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found");
        }
        return customer;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("id") int id, Customer updatedCustomer) {
        Customer existingCustomer = dataStore.getCustomers().get(id);
        if (existingCustomer == null) {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found");
        }

        // Basic validation
        if (updatedCustomer.getName() == null || updatedCustomer.getName().trim().isEmpty()) {
            throw new org.bookstore.exception.InvalidInputException("Customer name cannot be empty");
        }

        if (updatedCustomer.getEmail() == null || updatedCustomer.getEmail().trim().isEmpty() || !updatedCustomer.getEmail().contains("@")) {
            throw new org.bookstore.exception.InvalidInputException("Invalid email format");
        }

        if (updatedCustomer.getPassword() == null || updatedCustomer.getPassword().trim().isEmpty()) {
            throw new org.bookstore.exception.InvalidInputException("Password cannot be empty");
        }
        validateCustomerData(updatedCustomer);

        updatedCustomer.setId(id);
        dataStore.getCustomers().put(id, updatedCustomer);
        return Response.ok(updatedCustomer).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        Customer customer = dataStore.getCustomers().remove(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found");
        }

        // Also remove the customer's cart and orders
        dataStore.getCarts().remove(id);
        // We would remove orders too, but they need to be looked up by customer ID

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    private void validateCustomerData(Customer customer) {
        // Check for empty or null name
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new InvalidInputException("Customer name cannot be empty");
        }

        // Check for valid email format
        if (customer.getEmail() == null || !customer.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new InvalidInputException("Invalid email format");
        }

        // Check for password length
        if (customer.getPassword() == null || customer.getPassword().length() < 6) {
            throw new InvalidInputException("Password must be at least 6 characters long");
        }
    }
}