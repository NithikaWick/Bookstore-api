package org.example.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class AuthorNotFoundExceptionMapper implements ExceptionMapper<AuthorNotFoundException> {
    @Override
    public Response toResponse(AuthorNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"" + exception.getMessage() + "\"}")
                .type("application/json")
                .build();
    }
}