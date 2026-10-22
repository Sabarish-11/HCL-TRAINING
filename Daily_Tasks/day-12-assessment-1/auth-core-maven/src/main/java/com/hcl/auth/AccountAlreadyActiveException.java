package com.hcl.auth;

/** Unchecked exception for an invalid account-state transition. */
public class AccountAlreadyActiveException extends IllegalStateException {
    public AccountAlreadyActiveException(long id) {
        super("User account " + id + " is already active.");
    }
}
