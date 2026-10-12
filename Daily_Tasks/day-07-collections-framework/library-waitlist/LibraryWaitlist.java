import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class LibraryWaitlist {

    public static void main(String[] args) {

        // ArrayList is appropriate for the catalogue because:
        // - duplicates are allowed
        // - insertion order is preserved
        // - indexed access is efficient
        List<String> catalogue = new ArrayList<>();

        catalogue.add("Clean Code");
        catalogue.add("Effective Java");
        catalogue.add("Head First Java");
        catalogue.add("Design Patterns");

        System.out.println("===== LIBRARY CATALOGUE =====");

        for (String book : catalogue) {
            System.out.println(book);
        }

        System.out.println("Book at index 1: " + catalogue.get(1));

        // LinkedHashSet is appropriate for de-duplication while
        // preserving insertion order.
        Set<String> uniqueRequests = new LinkedHashSet<>();

        uniqueRequests.add("Clean Code");
        uniqueRequests.add("Effective Java");
        uniqueRequests.add("Clean Code");
        uniqueRequests.add("Head First Java");

        System.out.println("\n===== UNIQUE BOOK REQUESTS =====");

        for (String request : uniqueRequests) {
            System.out.println(request);
        }

        // ArrayDeque is appropriate for a normal FIFO waitlist.
        Deque<String> waitlist = new ArrayDeque<>();

        waitlist.addLast("Arun");
        waitlist.addLast("Priya");
        waitlist.addLast("Rahul");

        System.out.println("\n===== LIBRARY WAITLIST =====");

        for (String student : waitlist) {
            System.out.println(student);
        }

        System.out.println("Serving: " + waitlist.removeFirst());
        System.out.println("Next student: " + waitlist.peekFirst());

        // PriorityQueue is appropriate when requests need priority ordering.
        // Faculty requests receive higher priority than student requests.
        PriorityQueue<String> priorityRequests = new PriorityQueue<>(
                (a, b) -> {
                    boolean aFaculty = a.startsWith("FACULTY");
                    boolean bFaculty = b.startsWith("FACULTY");

                    if (aFaculty && !bFaculty) {
                        return -1;
                    }

                    if (!aFaculty && bFaculty) {
                        return 1;
                    }

                    return a.compareTo(b);
                }
        );

        priorityRequests.add("STUDENT - Arun");
        priorityRequests.add("FACULTY - Dr. Kumar");
        priorityRequests.add("STUDENT - Priya");
        priorityRequests.add("FACULTY - Dr. Meena");

        System.out.println("\n===== PRIORITY REQUESTS =====");

        while (!priorityRequests.isEmpty()) {
            System.out.println("Processing: " + priorityRequests.poll());
        }

        // Iterator.remove() safely removes elements during iteration.
        List<String> borrowedBooks = new ArrayList<>();

        borrowedBooks.add("Clean Code");
        borrowedBooks.add("Effective Java - OVERDUE");
        borrowedBooks.add("Head First Java");
        borrowedBooks.add("Design Patterns - OVERDUE");

        System.out.println("\n===== REMOVING OVERDUE BOOKS =====");

        Iterator<String> iterator = borrowedBooks.iterator();

        while (iterator.hasNext()) {
            String book = iterator.next();

            if (book.contains("OVERDUE")) {
                iterator.remove();
            }
        }

        for (String book : borrowedBooks) {
            System.out.println(book);
        }

        // Demonstration only: modifying a collection during
        // enhanced for-loop iteration causes ConcurrentModificationException.
        List<String> unsafeBooks = new ArrayList<>();

        unsafeBooks.add("Java Basics");
        unsafeBooks.add("Spring Boot");
        unsafeBooks.add("React");

        System.out.println("\n===== CONCURRENT MODIFICATION DEMO =====");

        try {
            for (String book : unsafeBooks) {
                if ("Java Basics".equals(book)) {
                    unsafeBooks.remove(book);
                }
            }
        } catch (java.util.ConcurrentModificationException ex) {
            System.out.println("Caught: " + ex.getClass().getSimpleName());
        }
    }
}