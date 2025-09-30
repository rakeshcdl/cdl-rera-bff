/**
 * ApplicationConfigurationNotFoundException.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 26/05/25
 */


package com.cdl.escrow.exception;

public class ApplicationConfigurationNotFoundException extends RuntimeException {
    public ApplicationConfigurationNotFoundException(String s) {
        super(s);
    }

    public ApplicationConfigurationNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}