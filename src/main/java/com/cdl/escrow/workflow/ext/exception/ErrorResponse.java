package com.cdl.escrow.workflow.ext.exception;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ErrorResponse {

    private int status;
    private String error;
    private String message;
    private ZonedDateTime timestamp;

    public ErrorResponse(int status, String error, String message, ZonedDateTime timestamp) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }
}
