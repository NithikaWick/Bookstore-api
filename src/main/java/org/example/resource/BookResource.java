package org.example.resource;

import org.example.DataStore;
import org.example.exception.BookNotFoundException;
import org.example.model.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("/books")
public class BookResource {
    private final DataStore dataStore = DataStore.getInstance();

    // GET /books - Retrieve all books
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAllBooks() {
        return new ArrayList<>(dataStore.getBooks().values());
    }

    // GET /books/{id} - Retrieve a book by ID
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

    // POST /books - Create a new book
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        int newId = dataStore.getNextBookId();
        book.setId(newId);
        dataStore.getBooks().put(newId, book);
        return Response.status(Response.Status.CREATED)
                .entity(book)
                .build();
    }

    // PUT /books/{id} - Update an existing book
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id") int id, Book updatedBook) {
        Book existingBook = dataStore.getBooks().get(id);
        if (existingBook == null) {
            throw new BookNotFoundException("Book with ID " + id + " not found");
        }
        updatedBook.setId(id);
        dataStore.getBooks().put(id, updatedBook);
        return Response.ok(updatedBook).build();
    }

    // DELETE /books/{id} - Delete a book
    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        Book book = dataStore.getBooks().get(id);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + id + " not found");
        }
        dataStore.getBooks().remove(id);
        return Response.noContent().build();
    }
}