# Booking System v1.0

A full-stack booking application built as a portfolio project during my System Developer studies.

![Booking System preview](docs/booking-system-preview.jpg)

## Features

- Create a booking
- View all bookings
- Update bookings through the REST API
- Cancel confirmed bookings
- Input validation for name, email, service and appointment time
- Clear HTTP 400 and 404 error responses
- MySQL persistence
- Responsive web interface
- Automated tests with JUnit and Mockito
- GitHub Actions CI

## Tech stack

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- MySQL
- Docker / Docker Compose
- Maven
- JUnit
- Mockito
- HTML
- CSS
- JavaScript

## Architecture

The application follows a simple layered structure:

```text
Web interface / API request
        ↓
BookingController
        ↓
BookingService
        ↓
BookingRepository
        ↓
MySQL
```

The controller receives HTTP requests, the service contains the booking logic, the repository communicates with the database, and MySQL stores the bookings.

## REST API

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/bookings` | Get all bookings |
| GET | `/api/bookings/{id}` | Get one booking |
| POST | `/api/bookings` | Create a booking |
| PUT | `/api/bookings/{id}` | Update a booking |
| PATCH | `/api/bookings/{id}/cancel` | Cancel a booking |

## Run locally

### 1. Start MySQL

```bash
docker compose up -d
```

If your installed Compose uses the standalone command instead:

```bash
docker-compose up -d
```

### 2. Start Spring Boot

Run `BookingsystemApplication.java` from IntelliJ, or use:

```bash
./mvnw spring-boot:run
```

By default the application is available at:

```text
http://localhost:8080
```

Database defaults are configured for MySQL on port `3306`. They can be overridden with `DB_URL`, `DB_USERNAME` and `DB_PASSWORD` environment variables.

## Testing

Run the automated tests with:

```bash
./mvnw test
```

GitHub Actions also runs the test suite automatically for pull requests and changes to `main`.

## Project status

**Version 1.0** — the core booking flow is complete and working.

## Author

Hussein Assaf
