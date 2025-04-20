package org.example.resource;

import org.example.DataStore;
import org.example.exception.AuthorNotFoundException;
import org.example.model.Author;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/authors")
public class AuthorResource {
    private final DataStore dataStore = DataStore.getInstance();

    // GET /authors - Retrieve all authors
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Author> getAllAuthors() {
        return new ArrayList<>(dataStore.getAuthors().values());
    }

    // GET /authors/{id} - Retrieve an author by ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Author getAuthorById(@PathParam("id") int id) {
        Author author = dataStore.getAuthors().get(id);
        if (author == null) {
            throw new AuthorNotFoundException("Author with ID " + id + " not found");
        }
        return author;
    }

    // POST /authors - Create a new author
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAuthor(Author author) {
        int newId = dataStore.getNextAuthorId();
        author.setId(newId);
        dataStore.getAuthors().put(newId, author);
        return Response.status(Response.Status.CREATED)
                .entity(author)
                .build();
    }
}