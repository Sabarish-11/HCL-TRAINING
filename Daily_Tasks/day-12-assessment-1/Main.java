
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskService service = new TaskService();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                System.out.println("\n=== KANBAN TASK MANAGER ===");
                System.out.println("1. Create task");
                System.out.println("2. View all tasks");
                System.out.println("3. Move task");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");

                String choice = scanner.nextLine().trim();

                try {
                    switch (choice) {
                        case "1":
                            System.out.print("Enter task title: ");
                            String title = scanner.nextLine();

                            Task created = service.createTask(title);
                            System.out.println("Created: " + created);
                            break;

                        case "2":
                            System.out.println("\n--- ALL TASKS ---");
                            if (service.getAllTasks().isEmpty()) {
                                System.out.println("No tasks found.");
                            } else {
                                service.getAllTasks()
                                        .forEach(System.out::println);
                            }
                            break;

                        case "3":
                            System.out.print("Enter task ID: ");
                            int id = Integer.parseInt(
                                    scanner.nextLine().trim());

                            System.out.println(
                                    "Statuses: TODO, IN_PROGRESS, DONE");
                            System.out.print("Enter new status: ");
                            String status = scanner.nextLine()
                                    .trim().toUpperCase();

                            service.moveTask(id, status);
                            System.out.println("Task moved successfully.");
                            break;

                        case "4":
                            running = false;
                            System.out.println("Goodbye!");
                            break;

                        default:
                            System.out.println(
                                    "Invalid menu option. Choose 1-4.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println(
                            "Invalid input. Please enter a numeric task ID.");
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }
}
