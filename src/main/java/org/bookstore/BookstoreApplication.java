package org.bookstore;

import javax.ws.rs.ApplicationPath;
import org.bookstore.exception.*;
import org.bookstore.resource.AuthorResource;
import org.bookstore.resource.BookResource;
import org.bookstore.resource.CartResource;
import org.bookstore.resource.CustomerResource;
import org.bookstore.resource.OrderResource;
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