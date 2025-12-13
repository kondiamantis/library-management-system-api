# Library Management System API

A Spring Boot REST API for managing library operations including user management, book catalog, and member borrowing functionality.

## Project Structure

```
src/
├── main/
│   ├── java/com/library/librarymanagementsystemapi/
│   │   ├── LibraryManagementSystemApiApplication.java
│   │   ├── controller/          # REST API endpoints
│   │   ├── service/             # Business logic
│   │   ├── repository/          # Data access layer
│   │   ├── entity/              # Entity classes
│   │   ├── dtos/                # Data Transfer Objects
│   │   ├── config/              # Configuration classes
│   │   ├── security/            # Security/Authentication
│   │   ├── validation/          # Validation logic
│   │   ├── converters/          # Enum converters
│   │   └── exception/           # Custom exceptions
│   └── resources/
│       └── application.properties
└── test/                        # Unit and integration tests
```

## Features

- **User Management**: Create and manage library users with different roles
- **Role-Based Access Control**: Admin and Member user types with distinct permissions
- **Book Management**: Add, update, search, and manage books in the catalog
- **Borrowing System**: Members can borrow and return books with tracking
- **User Authentication**: Secure API endpoints with JWT-based authentication
- **Dashboard**: Real-time statistics and library analytics
- **Data Initialization**: Automatic creation of default users on startup

## Getting Started

### Prerequisites

- Java 17 (or higher Java versions with LTS support)
- Maven 3.6+
- PostgreSQL

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd library-management-system-api
   ```

2. **Set up PostgreSQL Database**
   - **Option A**: Using command line (if `createdb` is in your PATH):
     ```bash
     createdb librarydb
     ```
   - **Option B**: Using SQL command in PostgreSQL:
     ```bash
     psql -U postgres -c "CREATE DATABASE librarydb;"
     ```
   - **Option C**: Using a GUI client like pgAdmin, DBeaver, or DataGrip to create a database named `librarydb`

3. **Configure Database Connection** (if needed)
   - Edit `src/main/resources/application.properties`
   - Update the database credentials if different from the defaults:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/librarydb
     spring.datasource.username=postgres
     spring.datasource.password=postgres
     ```

4. **Build the Project**
   ```bash
   mvn clean install
   ```

5. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```
   
   The application will start at `http://localhost:8080`

6. **Access the Application**
   - **API Base URL**: `http://localhost:8080`
   - **Swagger UI Documentation**: `http://localhost:8080/swagger-ui.html`
   - **API Docs (JSON)**: `http://localhost:8080/v3/api-docs`
   - **Test with default credentials** (see Default Users Created section above)

## Initial Setup

When the application starts, it automatically creates two default users via the data initialization process:

### Default Users Created:

1. **Admin User**
   - Email: `admin@library.com`
   - Password: `admin123`
   - Full system access and administrative privileges
   - Can manage books, users, and view all borrowing records
   - Role: ADMIN

2. **Member User**
   - Email: `member@library.com`
   - Password: `member123`
   - Standard user access for browsing and borrowing books
   - Can view available books and manage personal borrowing records
   - Role: MEMBER
   - Name: John Doe
   - Phone: 5551234567
   - Address: 123 Library Street

These users are automatically created during application startup if they don't already exist, allowing you to test the application immediately with these credentials.

### First-Time Setup Checklist

- [ ] Install Java 17 or higher
- [ ] Install Maven 3.6+
- [ ] Install PostgreSQL 12+
- [ ] Create `librarydb` database in PostgreSQL
- [ ] Update `application.properties` with your database credentials (if different)
- [ ] Run `mvn clean install`
- [ ] Run `mvn spring-boot:run`
- [ ] Access Swagger UI at `http://localhost:8080/swagger-ui.html`
- [ ] Login with admin credentials to verify setup

## API Documentation

The API is fully documented using **Swagger/OpenAPI 3.0**. After starting the application, you can view the interactive documentation at:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

The Swagger UI allows you to:
- Browse all available endpoints
- See request/response schemas
- Try endpoints directly from the browser
- View HTTP status codes and error messages

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/signup` - Register new user
- `GET /api/auth/me` - Get current user information

### Books
- `GET /api/books` - List all books
- `GET /api/books/{id}` - Get book details
- `POST /api/books` - Add a new book (Admin only)
- `PUT /api/books/{id}` - Update book (Admin only)
- `DELETE /api/books/{id}` - Delete book (Admin only)

### Members
- `GET /api/members` - List all members (Admin only)
- `GET /api/members/{id}` - Get member details
- `PUT /api/members/{id}` - Update member profile

### Borrowing
- `POST /api/borrowing/borrow` - Borrow a book
- `POST /api/borrowing/return` - Return a borrowed book
- `GET /api/borrowing/history` - View borrowing history

### Dashboard
- `GET /api/dashboard/stats` - Get library statistics (Admin only)

## Technology Stack

- **Framework**: Spring Boot 3.5.7
- **Language**: Java 17
- **Build Tool**: Maven
- **Database**: PostgreSQL with Spring Data JPA/Hibernate ORM
- **Authentication**: Spring Security with JWT (JSON Web Tokens)
- **API Documentation**: Swagger/OpenAPI 3.0 (SpringDoc)
- **Validation**: Jakarta Bean Validation (Annotations)
- **Project Management**: Lombok for boilerplate reduction

## Project Components

### Controllers
- `AuthController` - Authentication and user registration
- `BookController` - Book management endpoints
- `MemberController` - Member management endpoints
- `BorrowingController` - Borrowing operations
- `DashboardController` - Statistics and analytics

### Services
- Business logic layer implementing core functionality

### Repository
- Data access layer for database operations using Spring Data JPA

### Security
- JWT-based authentication and authorization
- Role-based access control

### Configuration
- Application-wide configurations and data initialization

## Running Tests

```bash
mvn test
```

## Troubleshooting

### Database Connection Issues
- **Error**: `connection refused` or `could not connect to database`
  - Ensure PostgreSQL is running
  - Verify database name is `librarydb`
  - Check credentials in `application.properties`
  - Run: `createdb librarydb`

### Port Already in Use
- **Error**: `Port 8080 is already in use`
  - Change port in `application.properties`: `server.port=8081`
  - Or kill the process using port 8080

### Java Version
- **Error**: `Unsupported class version`
  - Ensure you're using Java 17 or higher
  - Check: `java -version`

### Maven Build Issues
- **Error**: `BUILD FAILURE`
  - Try: `mvn clean` to remove cached dependencies
  - Update Maven: `mvn --version`

### Cannot Access Swagger UI
- **Error**: `404 Not Found` for `swagger-ui.html`
  - Ensure application started successfully
  - Verify application is running at `http://localhost:8080`
  - Check that the SpringDoc dependency is properly installed

## License

This project is licensed under the MIT License.

## Support

For issues, questions, or contributions, please refer to the project repository.

