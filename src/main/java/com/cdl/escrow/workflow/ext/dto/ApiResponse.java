package com.cdl.escrow.workflow.ext.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private ZonedDateTime timestamp;

    public static <T> ApiResponse<T> success(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        response.setTimestamp(ZonedDateTime.now());
        return response;
    }

    public static <T> ApiResponse<T> error(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setTimestamp(ZonedDateTime.now());
        return response;
    }

}
