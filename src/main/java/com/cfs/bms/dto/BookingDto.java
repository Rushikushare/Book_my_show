package com.cfs.bms.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

    private Long ID;
    private String bookingNumber;
    private LocalDateTime bookingTime;
    protected UserDto user;
    protected UserDto show;
    private String status;
    private double totalAmount;
    private List<ShoSeatDto> seats;
    private PaymentDto payment;

}