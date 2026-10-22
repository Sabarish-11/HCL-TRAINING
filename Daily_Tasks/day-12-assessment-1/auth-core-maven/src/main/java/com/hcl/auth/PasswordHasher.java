package com.hcl.auth;

/** Abstraction allows the hashing implementation to be replaced in tests or production. */
public interface PasswordHasher {
    String hash(char[] password);
}
