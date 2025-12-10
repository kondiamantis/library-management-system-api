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

1. Clone the repository
2. Navigate to the project directory:
   ```bash
   cd library-management-system-api
   ```
3. Configure database connection in `src/main/resources/application.properties`
4. Build and run the application:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

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

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/signup` - Register new user

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

- **Framework**: Spring Boot
- **Build Tool**: Maven
- **Database**: JPA/Hibernate ORM
- **Authentication**: Spring Security with JWT
- **Language**: Java
- **API Documentation**: RESTful API standards

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

## License

This project is licensed under the MIT License.

## Support

For issues, questions, or contributions, please refer to the project repository.

