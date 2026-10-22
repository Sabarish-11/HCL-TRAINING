package com.hcl.auth;

import java.util.List;
import java.util.Scanner;

public final class Main {
    private Main() {}

    public static void main(String[] args) {
        UserService service = new UserService(new InMemoryUserRepository(), new Pbkdf2PasswordHasher());

        // try-with-resources guarantees the Scanner is closed; no exception is silently swallowed.
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                printMenu();
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine().trim();

                try {
                    switch (choice) {
                        case "1" -> register(scanner, service);
                        case "2" -> activate(scanner, service);
                        case "3" -> printUsers(service.listUsersSorted());
                        case "4" -> System.out.println("Pending activation IDs: " + service.pendingActivationIds());
                        case "5" -> running = false;
                        default -> System.out.println("Choose a number from 1 to 5.");
                    }
                } catch (DuplicateUsernameException | InvalidUserDataException
                         | UserNotFoundException | AccountAlreadyActiveException exception) {
                    System.out.println("Error: " + exception.getMessage());
                } catch (IllegalStateException exception) {
                    System.out.println("Application error: " + exception.getMessage());
                }
            }
        }
        System.out.println("Goodbye.");
    }

    private static void printMenu() {
        System.out.println("\n=== Authentication Core Java Demo ===");
        System.out.println("1. Register account");
        System.out.println("2. Activate account");
        System.out.println("3. List accounts");
        System.out.println("4. Show pending activations");
        System.out.println("5. Exit");
    }

    private static void register(Scanner scanner, UserService service) throws DuplicateUsernameException {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password (minimum 8 characters): ");
        char[] password = scanner.nextLine().toCharArray();
        System.out.print("Account type (1=STANDARD, 2=ADMIN): ");
        String typeChoice = scanner.nextLine().trim();
        AccountType type = switch (typeChoice) {
            case "1" -> AccountType.STANDARD;
            case "2" -> AccountType.ADMIN;
            default -> throw new InvalidUserDataException("Account type must be 1 or 2.");
        };
        UserAccount user = service.registerUser(username, email, password, type);
        System.out.println("Registered successfully (inactive): " + user);
    }

    private static void activate(Scanner scanner, UserService service) {
        System.out.print("User ID to activate: ");
        long id;
        try {
            id = Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException exception) {
            throw new InvalidUserDataException("User ID must be a whole number.");
        }
        System.out.println("Activated: " + service.activateUser(id));
    }

    private static void printUsers(List<? extends UserAccount> users) {
        if (users.isEmpty()) {
            System.out.println("No accounts registered yet.");
            return;
        }
        users.forEach(System.out::println);
    }
}
