package org.bookstore.resource;

import org.bookstore.DataStore;
import org.bookstore.exception.AuthorNotFoundException;
import org.bookstore.model.Author;
import org.bookstore.model.Book;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/authors")
public class AuthorResource {
    private final DataStore dataStore = DataStore.getInstance();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAuthor(Author author) {
        int id = dataStore.getNextAuthorId();
        author.setId(id);
        dataStore.getAuthors().put(id, author);
        return Response.status(Response.Status.CREATED)
                .entity(author)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Author> getAllAuthors() {
        return dataStore.getAuthors().values().stream().collect(Collectors.toList());
    }

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

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAuthor(@PathParam("id") int id, Author updatedAuthor) {
        Author existingAuthor = dataStore.getAuthors().get(id);
        if (existingAuthor == null) {
            throw new AuthorNotFoundException("Author with ID " + id + " not found");
        }
        updatedAuthor.setId(id);
        dataStore.getAuthors().put(id, updatedAuthor);
        return Response.ok(updatedAuthor).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        Author author = dataStore.getAuthors().remove(id);
        if (author == null) {
            throw new AuthorNotFoundException("Author with ID " + id + " not found");
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/{id}/books")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getBooksByAuthor(@PathParam("id") int id) {
        if (!dataStore.getAuthors().containsKey(id)) {
            throw new AuthorNotFoundException("Author with ID " + id + " not found");
        }
        return dataStore.getBooks().values().stream()
                .filter(book -> book.getAuthorId() == id)
                .collect(Collectors.toList());
    }
}