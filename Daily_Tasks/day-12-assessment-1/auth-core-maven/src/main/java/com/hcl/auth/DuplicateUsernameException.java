package com.hcl.auth;

/** Checked exception: callers must handle duplicate usernames explicitly. */
public class DuplicateUsernameException extends Exception {
    public DuplicateUsernameException(String username) {
        super("Username '" + username + "' is already registered.");
    }
}
