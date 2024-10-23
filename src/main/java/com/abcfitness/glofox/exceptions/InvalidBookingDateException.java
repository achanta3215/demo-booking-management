package com.abcfitness.glofox.exceptions;

public class InvalidBookingDateException extends RuntimeException {
    public InvalidBookingDateException(String message) {
        super(message);
    }
}
