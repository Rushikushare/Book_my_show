package com.cfs.bms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScreenDto {

    private Long id;
    private String name;
    private Integer TotalSeats;
    private TheaterDto theater;

}
