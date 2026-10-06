public class MonthlyUsageAnalyser {

    public static void main(String[] args) {

        // 1-D array: usage for 12 months
        int[] monthlyUsage = {
            120, 150, 180, 90, 210, 175,
            160, 140, 200, 190, 130, 220
        };

        // Demonstrates long to prevent integer overflow
        long largeUsage = 3_000_000_000L;

        // 2-D array: usage for 3 houses over 12 months
        int[][] houseUsage = {
            {120, 150, 180, 90, 210, 175, 160, 140, 200, 190, 130, 220},
            {100, 130, 160, 80, 190, 165, 150, 120, 180, 170, 110, 200},
            {140, 170, 200, 100, 230, 185, 170, 160, 220, 210, 150, 240}
        };

        // Total, maximum and minimum
        long total = 0;
        int max = monthlyUsage[0];
        int min = monthlyUsage[0];

        for (int usage : monthlyUsage) {
            total += usage;

            if (usage > max) {
                max = usage;
            }

            if (usage < min) {
                min = usage;
            }
        }

        // Widening cast: long / int -> double
        double average = (double) total / monthlyUsage.length;

        // Narrowing cast: double -> int
        int roundedAverage = (int) average;

        // Ternary operator for grade
        char grade = average >= 200 ? 'A'
                : average >= 150 ? 'B'
                : average >= 100 ? 'C'
                : 'D';

        // Slab calculation using constants
        long slabAmount;

        if (total <= Constants.SLAB_1_LIMIT) {
            slabAmount = total * Constants.SLAB_1_RATE;
        } else if (total <= Constants.SLAB_2_LIMIT) {
            slabAmount = total * Constants.SLAB_2_RATE;
        } else {
            slabAmount = total * Constants.SLAB_3_RATE;
        }

        // Floating-point precision demonstration
        double precisionExample = 0.1 + 0.2;

        // Output
        System.out.println("===== Monthly Usage Analyser =====");
        System.out.println("Total usage: " + total);
        System.out.println("Average usage: " + average);
        System.out.println("Maximum usage: " + max);
        System.out.println("Minimum usage: " + min);
        System.out.println("Grade: " + grade);
        System.out.println("Slab amount: " + slabAmount);
        System.out.println("Average as int: " + roundedAverage);
        System.out.println("Large usage value: " + largeUsage);
        System.out.println("0.1 + 0.2 = " + precisionExample);

        // Display 2-D array
        System.out.println("\n===== Usage for 3 Houses =====");

        for (int house = 0; house < Constants.NUMBER_OF_HOUSES; house++) {
            System.out.print("House " + (house + 1) + ": ");

            for (int month = 0; month < Constants.MONTHS_IN_YEAR; month++) {
                System.out.print(houseUsage[house][month]);

                if (month < Constants.MONTHS_IN_YEAR - 1) {
                    System.out.print(", ");
                }
            }

            System.out.println();
        }
    }
}