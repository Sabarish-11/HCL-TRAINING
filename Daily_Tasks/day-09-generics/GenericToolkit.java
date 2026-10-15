
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GenericToolkit {

    // 1. Generic class: stores a key and a value.
    static class Pair<K, V> {
        private final K key;
        private final V value;

        Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        K getKey() {
            return key;
        }

        V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Pair{" + key + " = " + value + "}";
        }
    }

    // 2. Bounded generic class: T must extend Number.
    static class Box<T extends Number> {
        private final T value;

        Box(T value) {
            this.value = value;
        }

        T getValue() {
            return value;
        }

        double doubleValue() {
            return value.doubleValue();
        }
    }

    // 3. Bounded generic method.
    static <T extends Comparable<T>> T max(T first, T second) {
        return first.compareTo(second) >= 0 ? first : second;
    }

    // 4. PECS: Producer Extends.
    // Read numbers from a list without modifying it.
    static double sumNumbers(List<? extends Number> numbers) {
        double sum = 0;

        for (Number number : numbers) {
            sum += number.doubleValue();
        }

        return sum;
    }

    // 5. PECS: Consumer Super.
    // Add Integer values to a compatible destination list.
    static void addIntegers(
            List<? super Integer> destination, int count) {

        for (int i = 1; i <= count; i++) {
            destination.add(i);
        }
    }

    // 6. Read a simple CSV file using try-with-resources.
    // Expected header: name,price
    static List<String[]> readProductsCsv(Path path)
            throws IOException {

        List<String[]> products = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            boolean header = true;

            while ((line = reader.readLine()) != null) {
                if (header) {
                    header = false;
                    continue;
                }

                if (line.isBlank()) {
                    continue;
                }

                String[] columns = line.split(",", -1);

                if (columns.length != 2) {
                    throw new IOException(
                            "Invalid CSV row: " + line);
                }

                products.add(columns);
            }
        }

        return products;
    }

    // 7. Custom AutoCloseable resource.
    static class DemoResource implements AutoCloseable {
        private final String name;

        DemoResource(String name) {
            this.name = name;
            System.out.println("Opening " + name);
        }

        void use() {
            System.out.println("Using " + name);
        }

        @Override
        public void close() {
            System.out.println("Closing " + name);
        }
    }

    // 8. Demonstrate nested resource closing order.
    static void demonstrateCloseOrder() {
        System.out.println("\n--- AutoCloseable close order ---");

        try (DemoResource first = new DemoResource("Resource A");
             DemoResource second = new DemoResource("Resource B")) {

            first.use();
            second.use();

        } // Closes B first, then A.

        System.out.println("Resources closed.");
    }

    // 9. Demonstrate type erasure.
    static void demonstrateTypeErasure() {
        System.out.println("\n--- Type erasure ---");

        Pair<String, Integer> stringIntegerPair =
                new Pair<>("Apples", 5);

        Pair<Double, String> doubleStringPair =
                new Pair<>(2.5, "kg");

        System.out.println(
                "First pair class: "
                        + stringIntegerPair.getClass().getName());

        System.out.println(
                "Second pair class: "
                        + doubleStringPair.getClass().getName());

        System.out.println(
                "Same runtime class: "
                        + (stringIntegerPair.getClass()
                        == doubleStringPair.getClass()));
    }

    public static void main(String[] args) throws IOException {
        System.out.println("=== Generic Toolkit ===");

        // Generic Pair
        Pair<String, Integer> pair = new Pair<>("Apples", 5);
        System.out.println("\n--- Pair<K, V> ---");
        System.out.println(pair);
        System.out.println("Key: " + pair.getKey());
        System.out.println("Value: " + pair.getValue());

        // Bounded generic Box
        Box<Integer> integerBox = new Box<>(42);
        Box<Double> doubleBox = new Box<>(19.75);

        System.out.println("\n--- Box<T extends Number> ---");
        System.out.println("Integer box: " + integerBox.getValue());
        System.out.println("As double: " + integerBox.doubleValue());
        System.out.println("Double box: " + doubleBox.getValue());

        // Bounded max method
        System.out.println("\n--- Bounded max() ---");
        System.out.println("Max integer: " + max(15, 27));
        System.out.println("Max string: " + max("Apple", "Orange"));

        // Producer extends
        List<Integer> numbers = List.of(10, 20, 30);
        List<Double> decimalNumbers = List.of(1.5, 2.5, 3.5);

        System.out.println("\n--- Producer extends ---");
        System.out.println("Integer sum: " + sumNumbers(numbers));
        System.out.println("Decimal sum: " + sumNumbers(decimalNumbers));

        // Consumer super
        List<Number> destination = new ArrayList<>();
        addIntegers(destination, 3);

        System.out.println("\n--- Consumer super ---");
        System.out.println("Added integers: " + destination);

        // CSV reading
        System.out.println("\n--- CSV reading ---");

        Path csvPath = Path.of("products.csv");

        if (Files.exists(csvPath)) {
            List<String[]> products = readProductsCsv(csvPath);

            for (String[] product : products) {
                System.out.println(
                        "Product: " + product[0]
                                + ", Price: " + product[1]);
            }

            System.out.println("Products loaded: " + products.size());
        } else {
            System.out.println(
                    "products.csv not found in the current directory.");
            System.out.println(
                    "Create products.csv beside the Java file "
                            + "or run from that folder.");
        }

        // Type erasure
        demonstrateTypeErasure();

        // Resource close order
        demonstrateCloseOrder();

        System.out.println("\nGeneric Toolkit completed.");
    }
}
