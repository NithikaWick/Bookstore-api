package org.example.resource;

import org.example.DataStore;
import org.example.exception.AuthorNotFoundException;
import org.example.exception.BookNotFoundException;
import org.example.model.Book;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.stream.Collectors;

@Path("/books")
public class BookResource {
    private final DataStore dataStore = DataStore.getInstance();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        if (!dataStore.getAuthors().containsKey(book.getAuthorId())) {
            throw new AuthorNotFoundException("Author with ID " + book.getAuthorId() + " not found");
        }
        int id = dataStore.getNextBookId();
        book.setId(id);
        dataStore.getBooks().put(id, book);
        return Response.status(Response.Status.CREATED)
                .entity(book)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public java.util.List<Book> getAllBooks() {
        return dataStore.getBooks().values().stream().collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getBookById(@PathParam("id") int id) {
        Book book = dataStore.getBooks().get(id);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + id + " not found");
        }
        return book;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id") int id, Book updatedBook) {
        Book existingBook = dataStore.getBooks().get(id);
        if (existingBook == null) {
            throw new BookNotFoundException("Book with ID " + id + " not found");
        }
        if (!dataStore.getAuthors().containsKey(updatedBook.getAuthorId())) {
            throw new AuthorNotFoundException("Author with ID " + updatedBook.getAuthorId() + " not found");
        }
        updatedBook.setId(id);
        dataStore.getBooks().put(id, updatedBook);
        return Response.ok(updatedBook).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        Book book = dataStore.getBooks().remove(id);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + id + " not found");
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}