package com.cfs.bms.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowDto {

    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private MovieDto movie;
    private ScreenDto screen;
    private List<ShowSeatDto> availableSeats;

}
