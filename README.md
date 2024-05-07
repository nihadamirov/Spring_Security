# Spring Security Project

This project is a Spring Boot application for user authentication and authorization using 
 Spring Security with MySQL database and Docker.

## Prerequisites

Make sure you have the following installed:

- Java Development Kit (JDK-17)
- IntelliJ IDEA (or any preferred IDE)
- Postman (for testing API endpoints)
- Docker

## Getting Started

1. Clone the repository:

```bash
git clone https://github.com/nihadamirov/Spring_Security
```

2. Open the project in IntelliJ IDEA.

3. Configure MySQL database settings in `application.properties`:

```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/security_db
spring.datasource.username=
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

4. Build and run the application.

5. Test the API endpoints using Postman.

## Docker Setup

1. Make sure Docker is installed on your machine.

2. Create a Docker Compose file (`docker-compose.yml`) with the following configuration:

```yaml
version: '3.8'

services:
  db:
    image: mysql
    restart: always
    container_name: security-db
    environment:
      MYSQL_ROOT_PASSWORD:
      MYSQL_DATABASE:
    ports:
      - '3306:3306'
```

3. Run the following command to start the MySQL database container:

```bash
docker-compose up -d
```

## Project Structure

The project structure is as follows:

- **Model**: Contains entity classes representing users and roles.
- **Repository**: Provides interfaces for database operations.
- **Service**: Implements business logic and user authentication.
- **Controller**: Defines REST endpoints for user authentication and token generation.
- **Security**: Configures Spring Security for authentication and authorization.
- **Configuration**: Configures database and other application properties.

## Usage

- To add a new user, send a POST request to `/auth/registration` with the necessary user details in the request body.
- To generate a JWT token, send a POST request to `/auth/login` with the username and password in the request body.
- Access `/auth/user` endpoint for user-specific resources (requires USER role).
- Access `/auth/admin` endpoint for admin-specific resources (requires ADMIN role).

## Dependencies

- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL Database

----

## Additional Resources

Here are some additional resources for further learning:

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/index.html)
- [Spring Security Architecture--YouTube](https://youtu.be/h-9vhFeM3MY?si=zZsDtdrdzl6Cht9b)

