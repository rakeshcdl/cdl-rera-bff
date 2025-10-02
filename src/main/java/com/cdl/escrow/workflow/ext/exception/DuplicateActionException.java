package com.cdl.escrow.workflow.ext.exception;

public class DuplicateActionException extends RuntimeException {
    public DuplicateActionException(String message) {
        super(message);
    }
}
