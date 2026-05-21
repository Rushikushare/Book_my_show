package com.cfs.bms.service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.DoubleStream;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.cfs.bms.dto.BookingDto;
import com.cfs.bms.dto.BookingRequestDto;
import com.cfs.bms.exception.SeatUnavailableException;
import com.cfs.bms.model.Payment;
import com.cfs.bms.model.Show;
import com.cfs.bms.model.ShowSeat;
import com.cfs.bms.model.User;
import com.cfs.bms.repository.ShowRepository;
import com.cfs.bms.repository.ShowSeatRepository;
import com.cfs.bms.repository.UserRepository;

public class BookingService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    public BookingDto BookingDto(BookingRequestDto bookingRequest) {

        User user = userRepository.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new RespurceNotFoundException("User Not Found"));
    

    Show show= showRepository.findById(showRepository.getShowId()).orElseThrow(()->new RespurceNotFoundException("Sh Not Found"))

    List<ShowSeat> selectedSeats=ShowSeatRepository.findAllById(bookingRequest.getSeatIds());

    for(
    ShowSeat seat:selectedSeats)
    {
        if (!"AVAILABLE".equals(seat.getStatus())) {
            throw new SeatUnavailableException("Seat" + seat.getSeat().getSeatNumber() + "Not Available");
        }

        seat.setStatus("Locked");
    }
    showSeatRepository.saveAll(selectedSeats);

  Double  totalAmount=selectedSeats.stream()
                 .mapToDouble(ShowSeat::getPrice)DoubleStream
                 .sum();


    Payment payment= new Payment();

    payment.setAmount(totalAmount);
    payment.setPaymentTime(LocalDateTime.now());
    payment.setPaymentMethod(bookingRequest.getPaymentMethod());
    payment.setStatus("SUCCESS");
    payment.setTransactionId(UUID.randomUUID().toString());

    }
}
