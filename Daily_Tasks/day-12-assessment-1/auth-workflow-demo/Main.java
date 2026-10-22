import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserRepository repository = new UserRepository();
        UserService userService = new UserService(repository);

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                System.out.println("\n=== User Authentication Demo ===");
                System.out.println("1. Register user");
                System.out.println("2. Activate user");
                System.out.println("3. List users");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");

                String choice = scanner.nextLine().trim();

                try {
                    switch (choice) {
                        case "1":
                            System.out.print("Enter username: ");
                            String username = scanner.nextLine().trim();

                            System.out.print("Enter email: ");
                            String email = scanner.nextLine().trim();

                            System.out.print("Enter password: ");
                            String password = scanner.nextLine();

                            AppUser user = userService.registerUser(
                                    username, email, password);

                            System.out.println(
                                    "User registered successfully. ID: "
                                            + user.getId());
                            System.out.println(
                                    "Initial status: "
                                            + (user.isActive()
                                            ? "Active" : "Inactive"));
                            break;

                        case "2":
                            System.out.print("Enter user ID: ");
                            long id = Long.parseLong(
                                    scanner.nextLine().trim());

                            AppUser activated =
                                    userService.activateUser(id);

                            System.out.println(
                                    "User activated: "
                                            + activated.getUsername());
                            break;

                        case "3":
                            System.out.println("\n--- Registered Users ---");

                            if (userService.getAllUsers().isEmpty()) {
                                System.out.println("No users registered.");
                            } else {
                                for (AppUser registered
                                        : userService.getAllUsers()) {
                                    System.out.println(registered);
                                }
                            }
                            break;

                        case "4":
                            running = false;
                            System.out.println("Exiting demo.");
                            break;

                        default:
                            System.out.println(
                                    "Invalid option. Choose 1 to 4.");
                    }
                } catch (NumberFormatException exception) {
                    System.out.println(
                            "Invalid input. Please enter a numeric user ID.");
                } catch (IllegalArgumentException
                         | IllegalStateException exception) {
                    System.out.println(
                            "Operation failed: " + exception.getMessage());
                }
            }
        }
    }
}