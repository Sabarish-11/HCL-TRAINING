import java.util.Comparator;

public class DepartmentComparator implements Comparator<Employee> {

    private final Comparator<Employee> comparator =
            Comparator.comparing(Employee::getDepartment)
                    .thenComparing(Employee::getName)
                    .thenComparingInt(Employee::getId);

    @Override
    public int compare(Employee first, Employee second) {
        return comparator.compare(first, second);
    }
}