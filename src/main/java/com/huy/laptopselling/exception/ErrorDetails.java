package com.huy.laptopselling.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDetails {
    private Date timeStamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public ErrorDetails(Date timeStamp, int status, String error, String message, String path) {
        this.timeStamp = timeStamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
