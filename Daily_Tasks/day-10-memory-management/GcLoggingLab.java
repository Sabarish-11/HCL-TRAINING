public class GcLoggingLab {

    public static void main(String[] args) {
        for (int round = 1; round <= 12; round++) {
            // Each batch temporarily holds about 12 MiB of byte arrays.
            byte[][] batch = new byte[12][];

            for (int i = 0; i < batch.length; i++) {
                batch[i] = new byte[1024 * 1024];
            }

            System.out.println("Completed allocation batch " + round);
        }

        System.out.println("GC logging experiment complete.");
    }
}