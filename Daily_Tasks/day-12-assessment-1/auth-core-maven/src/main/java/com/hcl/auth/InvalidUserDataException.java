package com.hcl.auth;

/** Unchecked exception for invalid registration input. */
public class InvalidUserDataException extends IllegalArgumentException {
    public InvalidUserDataException(String message) {
        super(message);
    }
}
