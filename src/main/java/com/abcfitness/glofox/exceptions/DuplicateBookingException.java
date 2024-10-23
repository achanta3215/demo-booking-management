package com.abcfitness.glofox.exceptions;

public class DuplicateBookingException extends RuntimeException {
    public DuplicateBookingException(String message) {
        super(message);
    }
}
