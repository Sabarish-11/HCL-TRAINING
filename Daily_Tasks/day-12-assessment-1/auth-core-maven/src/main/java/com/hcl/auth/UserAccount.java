package com.hcl.auth;

import java.util.Locale;
import java.util.Objects;

/** Common state and behavior shared by all account types. */
public abstract class UserAccount implements Identifiable<Long>, Activatable, Comparable<UserAccount> {
    private final long id;
    private final String username;
    private final String email;
    private final String passwordHash;
    private boolean active;

    protected UserAccount(long id, String username, String email, String passwordHash) {
        if (id <= 0) {
            throw new InvalidUserDataException("User ID must be positive.");
        }
        if (username == null || username.isBlank()) {
            throw new InvalidUserDataException("Username cannot be blank.");
        }
        if (email == null || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new InvalidUserDataException("A valid email address is required.");
        }
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new InvalidUserDataException("Password hash cannot be blank.");
        }
        this.id = id;
        this.username = username.trim();
        this.email = email.trim();
        this.passwordHash = passwordHash;
        this.active = false;
    }

    @Override
    public final Long getId() {
        return id;
    }

    public final String getUsername() {
        return username;
    }

    public final String getEmail() {
        return email;
    }

    /** Intentionally no password-hash getter and never print the hash. */
    protected final String passwordHashForInternalUse() {
        return passwordHash;
    }

    @Override
    public final boolean isActive() {
        return active;
    }

    @Override
    public final void activate() {
        if (active) {
            throw new AccountAlreadyActiveException(id);
        }
        active = true;
    }

    public abstract AccountType getAccountType();

    @Override
    public int compareTo(UserAccount other) {
        int byName = username.toLowerCase(Locale.ROOT)
                .compareTo(other.username.toLowerCase(Locale.ROOT));
        return byName != 0 ? byName : Long.compare(id, other.id);
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof UserAccount other)) return false;
        return id == other.id;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserAccount{id=" + id + ", username='" + username + "', email='" + email
                + "', type=" + getAccountType() + ", active=" + active + "}";
    }
}
