package com.Hussein.bookingsystem.service;

import com.Hussein.bookingsystem.model.Booking;
import com.Hussein.bookingsystem.model.BookingStatus;
import com.Hussein.bookingsystem.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        bookingService = new BookingService(bookingRepository);
    }

    @Test
    void shouldReturnAllBookings() {
        Booking booking = createBooking();
        when(bookingRepository.findAll()).thenReturn(List.of(booking));

        List<Booking> result = bookingService.getAllBookings();

        assertEquals(1, result.size());
        assertEquals("Hussein", result.getFirst().getCustomerName());
    }

    @Test
    void shouldCreateConfirmedBooking() {
        Booking booking = createBooking();
        when(bookingRepository.save(booking)).thenReturn(booking);

        Booking result = bookingService.createBooking(booking);

        assertEquals(BookingStatus.CONFIRMED, result.getStatus());
        verify(bookingRepository).save(booking);
    }

    @Test
    void shouldCancelBooking() {
        Booking booking = createBooking();
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenReturn(booking);

        Booking result = bookingService.cancelBooking(1L);

        assertEquals(BookingStatus.CANCELLED, result.getStatus());
        verify(bookingRepository).save(booking);
    }

    @Test
    void shouldThrowExceptionWhenBookingDoesNotExist() {
        when(bookingRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> bookingService.getBookingById(99L));
    }

    private Booking createBooking() {
        return new Booking(
                "Hussein",
                "hussein@example.com",
                "Haircut",
                LocalDateTime.of(2026, 8, 20, 14, 0)
        );
    }
}
