package com.Hussein.bookingsystem.repository;

import com.Hussein.bookingsystem.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
