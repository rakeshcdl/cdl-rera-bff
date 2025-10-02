package com.cdl.escrow.workflow.ext.exception;

public class InvalidStateException extends RuntimeException {
    public InvalidStateException(String message) {
        super(message);
    }
}