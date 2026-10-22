public class UserServiceTest {
    public static void main(String[] args) {
        testRegistrationStartsInactive();
        testDuplicateUsernameRejected();
        testUserActivation();
        testMissingUserRejected();
        testAlreadyActiveUserRejected();

        System.out.println("All 5 UserService tests passed.");
    }

    private static void testRegistrationStartsInactive() {
        UserService service = new UserService(new UserRepository());

        AppUser user = service.registerUser(
                "alice", "alice@example.com", "SamplePass123!");

        assert user.getId() == 1;
        assert !user.isActive();
    }

    private static void testDuplicateUsernameRejected() {
        UserService service = new UserService(new UserRepository());
        service.registerUser("alice", "alice@example.com", "SamplePass123!");

        expectException(IllegalArgumentException.class, () ->
                service.registerUser(
                        "alice", "other@example.com", "OtherPass123!"));
    }

    private static void testUserActivation() {
        UserService service = new UserService(new UserRepository());
        AppUser user = service.registerUser(
                "bob", "bob@example.com", "SamplePass123!");

        AppUser activated = service.activateUser(user.getId());

        assert activated.isActive();
    }

    private static void testMissingUserRejected() {
        UserService service = new UserService(new UserRepository());

        expectException(IllegalArgumentException.class, () ->
                service.activateUser(999));
    }

    private static void testAlreadyActiveUserRejected() {
        UserService service = new UserService(new UserRepository());
        AppUser user = service.registerUser(
                "carol", "carol@example.com", "SamplePass123!");

        service.activateUser(user.getId());

        expectException(IllegalStateException.class, () ->
                service.activateUser(user.getId()));
    }

    private static void expectException(
            Class<? extends RuntimeException> expectedType,
            Runnable action) {
        try {
            action.run();
        } catch (RuntimeException exception) {
            assert expectedType.isInstance(exception)
                    : "Expected " + expectedType.getSimpleName()
                    + " but got " + exception.getClass().getSimpleName();
            return;
        }

        throw new AssertionError(
                "Expected exception: " + expectedType.getSimpleName());
    }
}