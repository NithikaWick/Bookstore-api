package org.example;

import jakarta.ws.rs.ApplicationPath;
import org.example.exception.*;
import org.example.resource.AuthorResource;
import org.example.resource.BookResource;
import org.example.resource.CartResource;
import org.example.resource.CustomerResource;
import org.example.resource.OrderResource;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class BookstoreApplication extends ResourceConfig {
    public BookstoreApplication() {
        // Register resource classes
        register(BookResource.class);
        register(AuthorResource.class);
        register(CustomerResource.class);
        register(CartResource.class);
        register(OrderResource.class);

        // Register exception mappers
        register(AuthorNotFoundExceptionMapper.class);
        register(BookNotFoundExceptionMapper.class);
        register(CartNotFoundExceptionMapper.class);
        register(CustomerNotFoundExceptionMapper.class);
        register(InvalidInputExceptionMapper.class);
        register(OrderNotFoundExceptionMapper.class);
        register(OutOfStockExceptionMapper.class);
        register(GenericExceptionMapper.class);

    }
}