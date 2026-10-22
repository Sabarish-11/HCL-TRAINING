public class StackOverflowLab {

    private static long calls = 0;

    private static void recurse() {
        calls++;

        // Keep each recursive call active so stack frames accumulate.
        recurse();
    }

    public static void main(String[] args) {
        try {
            recurse();
        } catch (StackOverflowError error) {
            System.out.println("Error: " + error);
            System.out.println("Recursive calls before overflow: " + calls);
        }
    }
}
