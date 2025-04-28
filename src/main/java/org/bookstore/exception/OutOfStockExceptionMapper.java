package org.bookstore.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class OutOfStockExceptionMapper implements ExceptionMapper<OutOfStockException> {
    @Override
    public Response toResponse(OutOfStockException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \"" + exception.getMessage() + "\"}")
                .type("application/json")
                .build();
    }
}