public class StringPerformanceLab {

    private static final int ITERATIONS = 30_000;

    private static long testStringConcatenation() {
        long start = System.nanoTime();

        String result = "";

        for (int i = 0; i < ITERATIONS; i++) {
            result += "x";
        }

        long elapsed = System.nanoTime() - start;

        // Use the result so the work remains observable.
        System.out.println("String += length: " + result.length());

        return elapsed;
    }

    private static long testStringBuilder() {
        long start = System.nanoTime();

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < ITERATIONS; i++) {
            result.append("x");
        }

        long elapsed = System.nanoTime() - start;

        System.out.println("StringBuilder length: " + result.length());

        return elapsed;
    }

    public static void main(String[] args) {
        // Warm up both approaches before measuring.
        for (int i = 0; i < 3; i++) {
            testStringConcatenation();
            testStringBuilder();
        }

        System.out.println("--- Timed runs ---");

        long concatTime = testStringConcatenation();
        long builderTime = testStringBuilder();

        System.out.printf(
                "String += time: %.3f ms%n",
                concatTime / 1_000_000.0);

        System.out.printf(
                "StringBuilder time: %.3f ms%n",
                builderTime / 1_000_000.0);
    }
}
