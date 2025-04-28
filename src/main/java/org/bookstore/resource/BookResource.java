package org.bookstore.resource;

import org.bookstore.DataStore;
import org.bookstore.exception.AuthorNotFoundException;
import org.bookstore.exception.BookNotFoundException;
import org.bookstore.exception.InvalidInputException;
import org.bookstore.model.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;

@Path("/books")
public class BookResource {
    private final DataStore dataStore = DataStore.getInstance();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        // Check if author exists
        if (!dataStore.getAuthors().containsKey(book.getAuthorId())) {
            throw new AuthorNotFoundException("Author with ID " + book.getAuthorId() + " not found");
        }

        // Validate book data
        validateBookData(book);

        // Set ID and save book
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

        // Validate updated book data
        validateBookData(updatedBook);

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
    // Helper method for book validation
    private void validateBookData(Book book) {
        // Check for empty or null title
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new InvalidInputException("Book title cannot be empty");
        }

        // Check for valid ISBN
        if (book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            throw new InvalidInputException("ISBN cannot be empty");
        }

        // Check for valid price (must be positive)
        if (book.getPrice() <= 0) {
            throw new InvalidInputException("Book price must be greater than 0");
        }

        // Check for valid stock (must be non-negative)
        if (book.getStock() < 0) {
            throw new InvalidInputException("Book stock cannot be negative");
        }

        // Check for valid publication year (not in the future)
        int currentYear = java.time.Year.now().getValue();
        if (book.getPublicationYear() > currentYear) {
            throw new InvalidInputException("Publication year cannot be in the future");
        }

        // Check for valid publication year (not unreasonably old)
        if (book.getPublicationYear() < 1400) {
            throw new InvalidInputException("Publication year is unreasonably old");
        }
    }

}