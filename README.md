# EventHub Platform

## Overview
EventHub is a Spring Boot application for venue discovery, reviews & ratings, user management, and RFP (Request for Proposal) workflows. It supports multiple user roles (BUYER, VENUE_OWNER, ADMIN) and provides secure authentication with JWT. Redis caching is used for performance optimization on venue search endpoints. The application uses a PostgreSQL database for persistent storage. This service powers the [`event-hub-ui`](https://github.com/shailendra98k/event-hub-ui) frontend project.

## Main Features
- **User Authentication & Authorization**: Signup, login, JWT-based authentication, role-based access control.
- **Venue Discovery**: Create, update, search, and filter venues by city, tags, and capacity. Venue owners can manage their venues.
- **Reviews & Ratings**: Buyers can submit reviews and ratings for venues. Server validates review eligibility.
- **RFP Management**: Buyers can submit RFPs to venues. Venue owners can view all RFPs submitted to their venues. RFP creation is idempotent (duplicate requests are prevented using Redis caching).
- **Redis Caching**: Venue search results are cached for faster response times.
- **Spring Security**: All endpoints are secured; role-based access is enforced.
- **CI/CD with GitHub Actions**: Automated build, test, and deployment pipeline using GitHub Actions.
- **Docker Support**: The application can be containerized and run using Docker for easy deployment.

## API Endpoints
### Auth
- `POST /auth/signup` - Register a new user (default role: BUYER)
- `POST /auth/login` - Login and receive JWT token

### Venues
- `POST /venues` - Create a new venue (VENUE_OWNER only)
- `GET /venues` - Search venues (filter by cities, tags, min/max capacity; all query params optional; **this endpoint is Redis cached for fast response**)
- `GET /venues/{id}` - Get venue details by ID

### Reviews (Under Development)
- `POST /venues/{id}/reviews` - Submit a review for a venue (BUYER only)
- `GET /venues/{id}/reviews` - Get paginated reviews for a venue

### RFPs
- `POST /rfps` - Create RFP (idempotent, BUYER only)
- `GET /api/v1/rfps` - Get paginated RFPs created by the current buyer
- `GET /api/v1/rfps` - Get all RFPs submitted to venues owned by the current VENUE_OWNER

### User Info
- `GET /user/info` - Fetch user info (validates JWT)

## Postman Collection
You can find the Postman collection for EventHub APIs here:
[EventHub Postman Collection](https://web.postman.co/workspace/My-Workspace~7dd2f8f1-8f90-42d2-841d-7db553af3d5b/collection/11123911-3031bff6-7f4b-4e8f-97c2-003f742864bd?action=share&source=copy-link&creator=11123911)


## Technologies Used
- **Spring Boot**
- **Spring Data JPA**
- **Spring Security**
- **JWT**
- **Redis**
- **PostgreSQL**
- **Lombok**
- **Maven**

## How to Run
1. Install Java 17+ and Maven 3.8+
2. Configure Redis (update `application.properties` with correct host, port, username, password)
3. Build: `mvn clean install`
4. Run: `mvn spring-boot:run`

## Running with Docker
You can run EventHub using Docker in two ways:

### Build and Run Locally
1. Build the Docker image:
   ```sh
   docker build -t eventhub .
   ```
2. Run the container:
   ```sh
   docker run -p 8080:8080 eventhub
   ```

### Use Pre-built Docker Image
You can pull and run the pre-built image used in ECS tasks:
```sh
docker pull shailendra98k/eventhub:master
docker run -p 8080:8080 shailendra98k/eventhub:master
```

## CI/CD
- The project includes a GitHub Actions workflow for continuous integration and deployment. On each push or pull request, the workflow will build, test, and optionally deploy the application.

## Custom Queries & Notes
- Venue filtering uses JPA Specifications for flexible search.
- Custom repository queries for fetching venues by owner and RFPs by venue.
- All entity field names must match JPA queries (e.g., `ownerUserId` in `Venue`).
- Validation errors return detailed messages in API responses.
- Role-based access is enforced using `@PreAuthorize` annotations.

## Troubleshooting
- **Redis Connection Refused**: Check Redis server status and credentials in `application.properties`.
- **JPA Query Errors**: Ensure entity field names match query attributes.
- **Validation Errors**: API returns detailed validation messages for request body issues.

## Reference Documentation
- [Spring Boot Reference](https://docs.spring.io/spring-boot/3.5.5/reference/)
- [Spring Data JPA](https://docs.spring.io/spring-boot/3.5.5/reference/data/sql.html#data.sql.jpa-and-spring-data)
- [Spring Security](https://docs.spring.io/spring-boot/3.5.5/reference/web/spring-security.html)
- [Spring Data Redis](https://docs.spring.io/spring-data/redis/docs/current/reference/html/)

## Project Structure
- `auth/` - Authentication, user management
- `venueDiscovery/` - Venue management and search
- `reviews/` - Reviews and ratings
- `rfps/` - RFP management
- `shared/` - Shared DTOs


---
For further details, see the source code and API documentation.
