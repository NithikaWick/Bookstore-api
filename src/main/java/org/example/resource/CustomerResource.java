package org.example.resource;

import org.example.DataStore;
import org.example.exception.CustomerNotFoundException;
import org.example.model.Cart;
import org.example.model.Customer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/customers")
public class CustomerResource {
    private final DataStore dataStore = DataStore.getInstance();

    // GET /customers - Retrieve all customers
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(dataStore.getCustomers().values());
    }

    // POST /customers - Create a new customer
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(Customer customer) {
        int newId = dataStore.getNextCustomerId();
        customer.setId(newId);
        dataStore.getCustomers().put(newId, customer);
        // Create an empty cart for the new customer
        dataStore.getCarts().put(newId, new Cart(newId));
        return Response.status(Response.Status.CREATED)
                .entity(customer)
                .build();
    }
}