package com.hcl.auth;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/** Business rules for the two assessment flows: register and activate. */
public final class UserService {
    private final UserRepository repository;
    private final PasswordHasher passwordHasher;
    private final AtomicLong nextId;

    public UserService(UserRepository repository, PasswordHasher passwordHasher) {
        if (repository == null || passwordHasher == null) {
            throw new IllegalArgumentException("Repository and password hasher are required.");
        }
        this.repository = repository;
        this.passwordHasher = passwordHasher;
        this.nextId = new AtomicLong(repository.findAll().stream()
                .mapToLong(UserAccount::getId).max().orElse(0L) + 1L);
    }

    public UserAccount registerUser(String username, String email, char[] password, AccountType type)
            throws DuplicateUsernameException {
        try {
            validateRegistration(username, email, password, type);
            if (repository.usernameExists(username)) {
                throw new DuplicateUsernameException(username);
            }

            String passwordHash = passwordHasher.hash(password);
            long id = nextId.getAndIncrement();
            UserAccount user = switch (type) {
                case STANDARD -> new StandardUser(id, username, email, passwordHash);
                case ADMIN -> new AdminUser(id, username, email, passwordHash);
            };
            repository.saveUser(user);
            return user;
        } finally {
            if (password != null) {
                java.util.Arrays.fill(password, '\0');
            }
        }
    }

    public UserAccount activateUser(long id) {
        UserAccount user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.activate();
        repository.markActivated(id);
        return user;
    }

    public List<UserAccount> listUsersSorted() {
        List<UserAccount> result = new ArrayList<>(repository.findAll());
        // Comparable provides natural username ordering; this Comparator provides a different view.
        result.sort(Comparator.comparing(UserAccount::getAccountType)
                .thenComparing(UserAccount::getUsername, String.CASE_INSENSITIVE_ORDER));
        return List.copyOf(result);
    }

    public List<Long> pendingActivationIds() {
        return repository.pendingActivationIds();
    }

    private void validateRegistration(String username, String email, char[] password, AccountType type) {
        if (username == null || username.isBlank()) {
            throw new InvalidUserDataException("Username cannot be blank.");
        }
        if (email == null || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new InvalidUserDataException("Enter a valid email address.");
        }
        if (password == null || password.length < 8) {
            throw new InvalidUserDataException("Password must contain at least 8 characters.");
        }
        if (type == null) {
            throw new InvalidUserDataException("Account type is required.");
        }
    }
}
