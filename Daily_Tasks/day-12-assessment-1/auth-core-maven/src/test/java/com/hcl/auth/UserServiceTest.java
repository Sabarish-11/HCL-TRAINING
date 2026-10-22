package com.hcl.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserRepository repository;
    private UserService service;

    @BeforeEach
    void setUp() {
        repository = new InMemoryUserRepository();
        // Test double keeps tests fast and isolates service behavior from cryptography.
        PasswordHasher testHasher = password -> "test-hash-" + password.length;
        service = new UserService(repository, testHasher);
    }

    @Test
    void registrationCreatesInactiveStandardUser() throws Exception {
        UserAccount user = service.registerUser("saba", "saba@example.com",
                "Password123".toCharArray(), AccountType.STANDARD);

        assertEquals(1L, user.getId());
        assertFalse(user.isActive());
        assertEquals(AccountType.STANDARD, user.getAccountType());
        assertFalse(user.toString().contains("test-hash"));
        assertEquals(List.of(1L), service.pendingActivationIds());
    }

    @Test
    void duplicateUsernameIsCaseInsensitiveAndChecked() throws Exception {
        service.registerUser("Saba", "one@example.com", "Password123".toCharArray(), AccountType.STANDARD);
        assertThrows(DuplicateUsernameException.class, () ->
                service.registerUser("saba", "two@example.com", "Password123".toCharArray(), AccountType.ADMIN));
    }

    @Test
    void activationChangesStateAndRemovesPendingId() throws Exception {
        UserAccount user = service.registerUser("user1", "u1@example.com",
                "Password123".toCharArray(), AccountType.ADMIN);

        service.activateUser(user.getId());

        assertTrue(user.isActive());
        assertTrue(service.pendingActivationIds().isEmpty());
    }

    @Test
    void missingUserThrowsCustomUncheckedException() {
        assertThrows(UserNotFoundException.class, () -> service.activateUser(999L));
    }

    @Test
    void activatingTwiceThrowsCustomUncheckedException() throws Exception {
        UserAccount user = service.registerUser("user2", "u2@example.com",
                "Password123".toCharArray(), AccountType.STANDARD);
        service.activateUser(user.getId());

        assertThrows(AccountAlreadyActiveException.class, () -> service.activateUser(user.getId()));
    }

    @Test
    void invalidRegistrationIsRejected() {
        assertThrows(InvalidUserDataException.class, () ->
                service.registerUser(" ", "bad-email", "short".toCharArray(), AccountType.STANDARD));
    }

    @Test
    void accountsAreSortedAndReturnedListCannotMutateRepository() throws Exception {
        service.registerUser("zara", "zara@example.com", "Password123".toCharArray(), AccountType.STANDARD);
        service.registerUser("alpha", "alpha@example.com", "Password123".toCharArray(), AccountType.STANDARD);

        List<UserAccount> result = service.listUsersSorted();
        assertEquals("alpha", result.get(0).getUsername());
        assertThrows(UnsupportedOperationException.class, () -> result.add(result.get(0)));
        assertEquals(2, repository.findAll().size());
    }

    @Test
    void passwordArrayIsClearedWhenRegistrationValidationFails() {
        char[] password = "short".toCharArray();

        assertThrows(InvalidUserDataException.class, () ->
                service.registerUser("valid", "valid@example.com", password, AccountType.STANDARD));

        assertArrayEquals(new char[password.length], password);
    }

    @Test
    void passwordArrayIsClearedWhenUsernameIsDuplicate() throws Exception {
        service.registerUser("duplicate", "one@example.com",
                "Password123".toCharArray(), AccountType.STANDARD);
        char[] password = "Password456".toCharArray();

        assertThrows(DuplicateUsernameException.class, () ->
                service.registerUser("DUPLICATE", "two@example.com", password, AccountType.ADMIN));

        assertArrayEquals(new char[password.length], password);
    }

    @Test
    void usernameLookupIgnoresCase() throws Exception {
        service.registerUser("Sabar", "sabar@example.com", "Password123".toCharArray(), AccountType.STANDARD);
        assertTrue(repository.findByUsername("sABAR").isPresent());
    }
}
