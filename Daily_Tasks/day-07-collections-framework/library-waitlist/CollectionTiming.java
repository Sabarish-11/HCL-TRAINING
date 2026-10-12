import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CollectionTiming {

    private static final int SIZE = 100_000;
    private static final int ACCESS_COUNT = 10_000;

    public static void main(String[] args) {

        System.out.println("===== COLLECTION TIMING =====");
        System.out.println("Size: " + SIZE);
        System.out.println("Indexed accesses: " + ACCESS_COUNT);

        long arrayListInsert = measureInsert(new ArrayList<>());
        long linkedListInsert = measureInsert(new LinkedList<>());

        List<Integer> arrayList = createList(new ArrayList<>());
        List<Integer> linkedList = createList(new LinkedList<>());

        long arrayListGet = measureGet(arrayList);
        long linkedListGet = measureGet(linkedList);

        System.out.println("\nOperation\t\tArrayList (ns)\tLinkedList (ns)");
        System.out.println("Insert\t\t\t" + arrayListInsert
                + "\t\t" + linkedListInsert);
        System.out.println("get(i)\t\t\t" + arrayListGet
                + "\t\t" + linkedListGet);

        System.out.println("\n===== OBSERVATION =====");
        System.out.println("ArrayList provides efficient indexed get(i) access.");
        System.out.println("LinkedList is better suited to frequent insertion/removal "
                + "through its linked structure.");
    }

    private static long measureInsert(List<Integer> list) {

        long start = System.nanoTime();

        for (int i = 0; i < SIZE; i++) {
            list.add(i);
        }

        return System.nanoTime() - start;
    }

    private static List<Integer> createList(List<Integer> list) {

        for (int i = 0; i < SIZE; i++) {
            list.add(i);
        }

        return list;
    }

    private static long measureGet(List<Integer> list) {

        long start = System.nanoTime();
        long total = 0;

        for (int i = 0; i < ACCESS_COUNT; i++) {
            total += list.get(i * 10);
        }

        // Prevent the JVM from treating the calculation as unused.
        if (total == -1) {
            System.out.println("Unexpected result.");
        }

        return System.nanoTime() - start;
    }
}