package com.cfs.bms.exception;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private Date timeStamp;
    private int status;
    private String msg;
    private String path;
}
