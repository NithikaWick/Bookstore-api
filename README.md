# Bookstore API
A RESTful API for a bookstore management system built with JAX-RS.
## Overview
This Bookstore API provides endpoints to manage authors, books, customers, shopping carts, and orders. It supports standard CRUD operations with appropriate validation and error handling. The API is designed to demonstrate RESTful principles using JAX-RS without relying on external libraries or frameworks beyond the core requirements.
## Key Features
- **Authors Management**: Create, read, update, and delete authors
- **Books Management**: Manage books with validation for prices, publication years, and availability
- **Customers Management**: Register and manage customer accounts
- **Shopping Cart**: Add, update, and remove items from customer carts
- **Orders**: Place orders and view order history
- **Error Handling**: Comprehensive exception mapping with appropriate HTTP status codes

## Technology Stack
- JAX-RS for RESTful web services
- JSON for data interchange
- Java EE standards-compliant implementation

## API Endpoints
### Authors
- `GET /api/authors` - Retrieve all authors
- `GET /api/authors/{id}` - Retrieve a specific author
- `POST /api/authors` - Create a new author
- `PUT /api/authors/{id}` - Update an existing author
- `DELETE /api/authors/{id}` - Delete an author
- `GET /api/authors/{id}/books` - Get all books by a specific author

### Books
- `GET /api/books` - Retrieve all books
- `GET /api/books/{id}` - Retrieve a specific book
- `POST /api/books` - Create a new book
- `PUT /api/books/{id}` - Update an existing book
- `DELETE /api/books/{id}` - Delete a book

### Customers
- `GET /api/customers` - Retrieve all customers
- `GET /api/customers/{id}` - Retrieve a specific customer
- `POST /api/customers` - Create a new customer
- `PUT /api/customers/{id}` - Update an existing customer
- `DELETE /api/customers/{id}` - Delete a customer

### Cart
- `GET /api/customers/{customerId}/cart` - View a customer's cart
- `POST /api/customers/{customerId}/cart/items` - Add an item to the cart
- `PUT /api/customers/{customerId}/cart/items/{bookId}` - Update cart item quantity
- `DELETE /api/customers/{customerId}/cart/items/{bookId}` - Remove an item from the cart

### Orders
- `GET /api/customers/{customerId}/orders` - Retrieve all orders for a customer
- `GET /api/customers/{customerId}/orders/{orderId}` - Retrieve a specific order
- `POST /api/customers/{customerId}/orders` - Create a new order from the cart

## Error Handling
The API implements comprehensive error handling with appropriate HTTP status codes:
- `400 Bad Request` - Invalid input or business rule violation
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Unexpected server errors

Each error response includes a descriptive message to help debug the issue.
