
import java.util.Comparator;

public class SalaryComparator implements Comparator<Employee> {

    private final Comparator<Employee> comparator =
            Comparator.comparingDouble(Employee::getSalary)
                    .reversed()
                    .thenComparing(Employee::getName)
                    .thenComparingInt(Employee::getId);

    @Override
    public int compare(Employee first, Employee second) {
        return comparator.compare(first, second);
    }
}