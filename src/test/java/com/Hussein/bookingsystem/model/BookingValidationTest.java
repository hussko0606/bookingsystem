package com.Hussein.bookingsystem.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookingValidationTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void closeValidatorFactory() {
        validatorFactory.close();
    }

    @Test
    void shouldAcceptValidBooking() {
        Booking booking = new Booking(
                "Hussein",
                "hussein@example.com",
                "Haircut",
                LocalDateTime.now().plusDays(2)
        );

        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);

        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldRejectBlankCustomerName() {
        Booking booking = new Booking(
                "",
                "hussein@example.com",
                "Haircut",
                LocalDateTime.now().plusDays(2)
        );

        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);

        assertTrue(violations.stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("customerName")));
    }

    @Test
    void shouldRejectInvalidEmail() {
        Booking booking = new Booking(
                "Hussein",
                "not-an-email",
                "Haircut",
                LocalDateTime.now().plusDays(2)
        );

        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);

        assertTrue(violations.stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("customerEmail")));
    }

    @Test
    void shouldRejectPastAppointmentTime() {
        Booking booking = new Booking(
                "Hussein",
                "hussein@example.com",
                "Haircut",
                LocalDateTime.now().minusDays(1)
        );

        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);

        assertEquals(1, violations.stream()
                .filter(violation -> violation.getPropertyPath().toString().equals("appointmentTime"))
                .count());
    }
}
