import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;

public class CollectionVariants {

    public static void main(String[] args) {

        // HashSet: unique elements, but no guaranteed iteration order.
        Set<String> hashSet = new HashSet<>();
        hashSet.add("Java");
        hashSet.add("Spring");
        hashSet.add("Java");

        System.out.println("===== HASHSET =====");
        System.out.println(hashSet);

        // TreeSet: unique elements in sorted order.
        Set<String> treeSet = new TreeSet<>();
        treeSet.add("Java");
        treeSet.add("Spring");
        treeSet.add("React");
        treeSet.add("Java");

        System.out.println("\n===== TREESET =====");
        System.out.println(treeSet);

        // ListIterator: supports forward and backward traversal
        // and safe modification of a List during iteration.
        List<String> technologies = new ArrayList<>();
        technologies.add("Java");
        technologies.add("Spring");
        technologies.add("React");

        System.out.println("\n===== LISTITERATOR FORWARD =====");

        ListIterator<String> iterator = technologies.listIterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println("\n===== LISTITERATOR BACKWARD =====");

        while (iterator.hasPrevious()) {
            System.out.println(iterator.previous());
        }

        System.out.println("\n===== FOREACH =====");

        for (String technology : technologies) {
            System.out.println(technology);
        }

        System.out.println("\n===== COLLECTION CHOICES =====");
        System.out.println("HashSet  -> unique elements, no ordering guarantee");
        System.out.println("TreeSet  -> unique elements, sorted order");
        System.out.println("ListIterator -> bidirectional List traversal and modification");
        System.out.println("for-each -> simple read-only iteration");
    }
}