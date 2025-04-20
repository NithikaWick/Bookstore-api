# Bookstore API

A "Bookstore" application API that allows interaction with entities such as books, authors, customers, shopping carts, and orders. 
The focus is on applying RESTful principles and using the JAX-RS framework to create a robust, scalable backend API. 
The project simulates a real-world e-commerce scenario, emphasizing hands-on learning in API design, implementation, and testing.

---

## Key Components

### Technology Stack
- **JAX-RS**: Used for RESTful service implementation (Jersey).
- **JSON**: Data format for requests and responses.
- **Postman**: For API testing and demonstration.
- **Apache Tomcat Server**: for deploy and serve this java based project.
- **In-memory Data Structures**: Use of `ArrayList` and `HashMap` for data storage (no external databases or persistence frameworks).

---

### Resource Classes and Endpoints
- Implement specific endpoints for managing:
  - **Books**: e.g., `POST /books`, `GET /books/{id}`, `DELETE /books/{id}`.
  - **Authors**: e.g., `POST /authors`, `GET /authors/{id}`.
  - **Customers**: e.g., `POST /customers`, `GET /customers/{id}`.
  - **Carts**: e.g., `POST /carts`, `GET /carts/{id}`.
  - **Orders**: e.g., `POST /orders`, `GET /orders/{id}`.
- Each resource class (e.g., `BookResource`, `AuthorResource`) handles appropriate HTTP methods (`GET`, `POST`, `PUT`, `DELETE`).

---

### Data Models
- Create Java classes for entities such as:
  - `Book`: With attributes like `id`, `title`, `author`, `price`, etc.
  - `Author`: With attributes like `id`, `name`, `biography`, etc.
  - `Customer`: With attributes like `id`, `name`, `email`, etc.
- Include relevant constructors, getters, and setters.

---

### Exception Handling
- Implement custom exceptions such as:
  - `BookNotFoundException`
  - `InvalidInputException`
- Use `ExceptionMapper` to handle errors with appropriate HTTP status codes and JSON error messages.

---

