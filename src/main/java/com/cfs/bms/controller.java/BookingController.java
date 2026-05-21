package com.cfs.bms.controller.java;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cfs.bms.dto.BookingDto;
import com.cfs.bms.dto.BookingRequestDto;
import com.cfs.bms.service.BookingService;

import jakarta.validation.Valid;
import lombok.Value;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@Valid @RequestBody BookingRequestDto bookingRequest){
        return ResponseEntity<>(bookingService.createBooking(bookingRequest),HttpStatus.CREATED)
    }
}
