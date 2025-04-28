package org.bookstore.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.WebApplicationException;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {
        if (exception instanceof WebApplicationException) {
            WebApplicationException webapp_ex = (WebApplicationException) exception;
            return Response.status(webapp_ex.getResponse().getStatus())
                    .entity("{\"error\": \"" + exception.getClass().getSimpleName() + "\", \"message\": \"" + exception.getMessage() + "\"}")
                    .type("application/json")
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"error\": \"InternalServerError\", \"message\": \"" + exception.getMessage() + "\"}")
                .type("application/json")
                .build();
    }
}