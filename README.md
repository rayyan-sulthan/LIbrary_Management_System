# Library Management System

Backend REST API built using Spring Boot for managing books, members, and borrow-return operations.  
The application implements JWT-based authentication and role-based authorization to secure endpoints and enforce business rules.

---

## Tech Stack

- Java 21  
- Spring Boot 3  
- Spring Security  
- JWT (JSON Web Token)  
- Spring Data JPA  
- Hibernate  
- Oracle SQL  
- Swagger (OpenAPI)  
- Maven  

---

## Features

### Authentication & Authorization
- User registration and login  
- JWT-based authentication  
- Role-based access control (ADMIN / USER)  
- Stateless session management  
- Password encryption using BCrypt  

### Book Management
- Create, update, and delete books (Admin only)  
- Search and filter books  
- Pagination and sorting support  
- Book availability tracking  

### Borrow & Return Management
- Borrow books with availability validation  
- Return books with status updates  
- Transactional service-layer logic  
- Borrow limit enforcement  

### Exception Handling
- Centralized global exception handling  
- Validation error responses  
- Appropriate HTTP status codes  

### API Documentation
- Integrated Swagger UI  
- JWT authorization supported within Swagger  

---

## Running the Application

### 1. Clone the repository

```bash
git clone https://github.com/rayyan-sulthan/LIbrary_Management_System.git
cd LIbrary_Management_System
```

### 2. Configure the database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Build and run

```bash
mvn clean install
mvn spring-boot:run
```

Application runs at:

```
http://localhost:8080
```

---

## API Documentation

Swagger UI is available at:

```
http://localhost:8080/swagger-ui/index.html
```

### Authenticating in Swagger

1. Call:
```
POST /api/auth/login
```

2. Copy the returned JWT token  
3. Click **Authorize** in Swagger  
4. Enter:
```
Bearer <your_token>
```

---

## Authentication Endpoints

```
POST /api/auth/register
POST /api/auth/login
```

The login endpoint returns a JWT token required to access protected APIs.

---

## Architecture Overview

```
Controller → Service → Repository → Database
```

Security flow:

```
JWT Filter → Spring Security → AuthenticationManager
```

---

## Key Concepts Demonstrated

- Implementation of JWT authentication in Spring Boot  
- Role-based authorization using Spring Security  
- Transaction management at the service layer  
- ORM integration with Hibernate and Oracle SQL  
- REST API documentation using OpenAPI (Swagger)  
- Layered architecture and separation of concerns  

---

## Author

Rayyan Sulthan  
Software Developer  

GitHub: https://github.com/rayyan-sulthan
