package com.hcl.auth;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Standard-library PBKDF2 example for this console exercise.
 * Production applications should use a reviewed authentication library and policy.
 */
public final class Pbkdf2PasswordHasher implements PasswordHasher {
    private static final int ITERATIONS = 120_000;
    private static final int KEY_LENGTH_BITS = 256;
    private static final int SALT_LENGTH_BYTES = 16;
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public String hash(char[] password) {
        if (password == null || password.length == 0) {
            throw new InvalidUserDataException("Password cannot be empty.");
        }
        byte[] salt = new byte[SALT_LENGTH_BYTES];
        secureRandom.nextBytes(salt);
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH_BITS);
        try {
            byte[] derived = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(spec).getEncoded();
            return ITERATIONS + ":" + Base64.getEncoder().encodeToString(salt)
                    + ":" + Base64.getEncoder().encodeToString(derived);
        } catch (GeneralSecurityException exception) {
            throw new IllegalStateException("Unable to hash password with PBKDF2.", exception);
        } finally {
            spec.clearPassword();
        }
    }
}
