package com.cfs.bms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDto {

    private Long showID;
    protected Long showId;
    private List<Long> seatIds;
    private String paymentMethod;

}
