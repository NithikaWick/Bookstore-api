package org.example.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InsufficientStockExceptionMapper implements ExceptionMapper<InsufficientStockException> {
    @Override
    public Response toResponse(InsufficientStockException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \"" + exception.getMessage() + "\"}")
                .type("application/json")
                .build();
    }
}