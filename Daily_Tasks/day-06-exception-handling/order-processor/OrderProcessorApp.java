import java.util.Scanner;

public class OrderProcessorApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        OrderProcessor processor = new OrderProcessor();

        boolean running = true;

        while (running) {
            System.out.println("\n===== ORDER PROCESSOR =====");
            System.out.println("1. Process valid order");
            System.out.println("2. Test invalid quantity");
            System.out.println("3. Test insufficient stock");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        processor.processOrder("Laptop", 2, 10);
                        break;

                    case "2":
                        processor.processOrder("Keyboard", 0, 10);
                        break;

                    case "3":
                        processor.processOrder("Monitor", 15, 5);
                        break;

                    case "4":
                        running = false;
                        System.out.println("Exiting Order Processor.");
                        break;

                    default:
                        System.out.println("Invalid menu option.");
                }

            } catch (OrderProcessingException | InvalidQuantityException ex) {
                System.out.println("Error: " + ex.getMessage());

                if (ex.getCause() != null) {
                    System.out.println("Original cause: "
                            + ex.getCause().getMessage());
                }

            } finally {
                System.out.println("Audit: order attempt completed.");
            }
        }

        scanner.close();
        System.out.println("Application closed.");
    }
}