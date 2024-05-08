Ən başa olmaqla, README.md faylınızda proyektinizin adını, qısa təsviri və quraşdırılması üçün təlimatlar yer almalıdır. Sonra proyektin məqsədi, funksiyaları, istifadəsi və əsas elementləri barədə məlumatlar əlavə edilə bilər. Məlumatları analiz edib README.md faylına yazım:

```markdown
# JWT Token Authentication Project

This project implements JWT (JSON Web Token) token-based authentication in a Spring Boot application. It provides user registration, login, password reset functionality, and role-based access control.

## Installation

To run this project locally, you need to have Docker installed on your machine. Then, clone the repository and navigate to its directory. Use Docker Compose to start the MySQL database container:

```bash
docker-compose up -d
```

After that, you can build and run the Spring Boot application using Maven:

```bash
mvn spring-boot:run
```

The application will be accessible at `http://localhost:8080`.

## Features

- User registration and authentication
- Role-based access control (USER and ADMIN roles)
- Password reset functionality via OTP (One-Time Password) verification
- JWT token generation and validation
- Secure password storage using bcrypt encryption

## Usage

### User Registration

To register a new user, send a POST request to `/auth/registration` with the following JSON payload:

```json
{
  "name": "John Doe",
  "username": "johndoe",
  "email": "johndoe@example.com",
  "password": "password123",
  "authorities": ["ROLE_USER"]
}
```

### User Login

To authenticate a user and generate a JWT token, send a POST request to `/auth/login` with the following JSON payload:

```json
{
  "username": "johndoe",
  "password": "password123"
}
```

### Password Reset

To reset a user's password, the user must request a verification code via email. Send a POST request to `/api/password/verifyMail/{email}` to initiate the process. Then, verify the OTP (One-Time Password) by sending a POST request to `/api/password/verifyOtp/{otp}/{email}`. Finally, change the password by sending a POST request to `/api/password/change/{email}` with the new password in the request body.

## Dependencies

- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL database
- JWT (JSON Web Token) library
- Lombok for reducing boilerplate code
- Maven for dependency management

## Configuration

- `application.properties`: Configuration for Spring Data JPA and database connection.
- `SecurityConfig`: Configuration for Spring Security and JWT authentication.
- `JwtService`: Service class for JWT token generation and validation.
- `UserService`: Service class for user management and authentication.

## Docker Compose

The `docker-compose.yml` file is provided to set up a MySQL database container for development purposes.

