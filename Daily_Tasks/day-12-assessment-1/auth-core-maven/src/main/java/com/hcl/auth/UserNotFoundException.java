package com.hcl.auth;

/** Unchecked exception for a requested account that does not exist. */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(long id) {
        super("No user account exists with ID " + id + ".");
    }
}
