package com.Hussein.bookingsystem.service;

import com.Hussein.bookingsystem.model.Booking;
import com.Hussein.bookingsystem.model.BookingStatus;
import com.Hussein.bookingsystem.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with id: " + id));
    }

    public Booking createBooking(Booking booking) {
        booking.setStatus(BookingStatus.CONFIRMED);
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Long id, Booking updatedBooking) {
        Booking booking = getBookingById(id);
        booking.setCustomerName(updatedBooking.getCustomerName());
        booking.setCustomerEmail(updatedBooking.getCustomerEmail());
        booking.setServiceName(updatedBooking.getServiceName());
        booking.setAppointmentTime(updatedBooking.getAppointmentTime());
        return bookingRepository.save(booking);
    }

    public Booking cancelBooking(Long id) {
        Booking booking = getBookingById(id);
        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }
}
