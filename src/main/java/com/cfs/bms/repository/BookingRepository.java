package com.cfs.bms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cfs.bms.model.Booking;

public interface BookingRepository extends JpaRepository<BookingRepository, Long> {

    List<Booking> findByUserId(Long userId);

    List<Booking> findByBookingNumber(String bookinNumber);

    List<Booking> findByShowId(Long id);

}
