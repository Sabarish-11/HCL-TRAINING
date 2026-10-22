import java.util.ArrayList;
import java.util.List;

public class HeapExhaustionLab {

    public static void main(String[] args) {
        List<byte[]> allocations = new ArrayList<>();
        long totalAllocated = 0;
        int chunkSize = 1024 * 1024; // 1 MiB per chunk

        try {
            while (true) {
                allocations.add(new byte[chunkSize]);
                totalAllocated += chunkSize;

                if (totalAllocated % (8L * chunkSize) == 0) {
                    System.out.printf(
                            "Requested approximately %d MiB%n",
                            totalAllocated / chunkSize);
                }
            }
        } catch (OutOfMemoryError error) {
            System.out.println("Error: " + error);
            System.out.printf(
                    "Requested before exhaustion: approximately %d MiB%n",
                    totalAllocated / chunkSize);
        }
    }
}