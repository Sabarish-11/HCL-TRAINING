public class StackTraceDemo {

    public static void main(String[] args) {

        OrderProcessor processor = new OrderProcessor();

        if (args.length == 0) {
            System.out.println("Use: invalid or stock");
            return;
        }

        try {
            if ("invalid".equalsIgnoreCase(args[0])) {
                processor.processOrder("Keyboard", 0, 10);
            } else if ("stock".equalsIgnoreCase(args[0])) {
                processor.processOrder("Monitor", 15, 5);
            } else {
                System.out.println("Unknown option.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}