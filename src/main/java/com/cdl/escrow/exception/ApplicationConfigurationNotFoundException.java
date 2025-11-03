/**
 * ApplicationConfigurationNotFoundException.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 26/05/25
 */


package com.cdl.escrow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ApplicationConfigurationNotFoundException extends RuntimeException {
    public ApplicationConfigurationNotFoundException(String s) {
        super(s);
    }

    public ApplicationConfigurationNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String msg){ super(msg); }
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    public static class ConflictException extends RuntimeException {
        public ConflictException(String msg){ super(msg); }
    }
}