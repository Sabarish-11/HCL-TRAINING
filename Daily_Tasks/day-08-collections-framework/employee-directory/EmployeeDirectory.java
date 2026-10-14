import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.Set;

public class EmployeeDirectory {

    public static void main(String[] args) {

        List<Employee> employees = List.of(
                new Employee(104, "Priya", "Engineering", 85000),
                new Employee(101, "Arun", "Engineering", 95000),
                new Employee(107, "Meena", "HR", 72000),
                new Employee(103, "Karthik", "Sales", 78000),
                new Employee(106, "Divya", "Engineering", 95000),
                new Employee(102, "Rahul", "Sales", 68000),
                new Employee(108, "Nila", "HR", 81000),
                new Employee(105, "Vijay", "Finance", 76000)
        );

        // 1. HashMap: look up employees by ID.
        Map<Integer, Employee> employeeById = new HashMap<>();

        for (Employee employee : employees) {
            employeeById.put(employee.getId(), employee);
        }

        System.out.println("=== EMPLOYEE LOOKUP ===");
        System.out.println(employeeById.get(103));
        System.out.println("Missing ID 999: " + employeeById.get(999));

        // 2. LinkedHashMap: preserve insertion order.
        Map<Integer, String> insertionOrder = new LinkedHashMap<>();

        for (Employee employee : employees) {
            insertionOrder.put(employee.getId(), employee.getName());
        }

        System.out.println("\n=== LINKEDHASHMAP: INSERTION ORDER ===");
        insertionOrder.forEach((id, name) ->
                System.out.println(id + " -> " + name));

        // 3. Count employees by department using Map.merge().
        Map<String, Integer> departmentCounts = new HashMap<>();

        for (Employee employee : employees) {
            departmentCounts.merge(
                    employee.getDepartment(), 1, Integer::sum);
        }

        System.out.println("\n=== DEPARTMENT FREQUENCY ===");
        departmentCounts.forEach((department, count) ->
                System.out.println(department + ": " + count));

        // 4. TreeMap: sort departments alphabetically.
        Map<String, Integer> sortedDepartmentCounts =
                new TreeMap<>(departmentCounts);

        System.out.println("\n=== TREEMAP: SORTED DEPARTMENTS ===");
        sortedDepartmentCounts.forEach((department, count) ->
                System.out.println(department + ": " + count));

        // 5. Comparable: natural ordering by employee ID.
        System.out.println("\n=== NATURAL ORDER: EMPLOYEE ID ===");
        Set<Employee> employeesById = new TreeSet<>(employees);
        employeesById.forEach(System.out::println);

        // 6. Comparator: department, then name, then ID.
        System.out.println("\n=== SORTED BY DEPARTMENT AND NAME ===");
        employees.stream()
                .sorted(new DepartmentComparator())
                .forEach(System.out::println);

        // 7. Comparator chain: highest salary first, then name and ID.
        System.out.println("\n=== TOP 5 SALARIES ===");
        employees.stream()
                .sorted(new SalaryComparator())
                .limit(5)
                .forEach(System.out::println);

        // 8. Sort map entries by key.
        System.out.println("\n=== EMPLOYEES SORTED BY ID (MAP ENTRIES) ===");
        employeeById.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry ->
                        System.out.println(entry.getKey() + " -> "
                                + entry.getValue().getName()));

        // 9. Sort department counts by descending count, then name.
        System.out.println("\n=== DEPARTMENTS SORTED BY COUNT ===");
        departmentCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue()
                        .reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(entry ->
                        System.out.println(entry.getKey() + ": "
                                + entry.getValue()));

        // 10. Demonstrate the equals() and hashCode() contract.
        System.out.println("\n=== EQUALS AND HASHCODE CONTRACT ===");

        Employee original =
                new Employee(101, "Arun", "Engineering", 95000);

        Employee duplicateId =
                new Employee(101, "Different Name", "HR", 50000);

        System.out.println("Employees equal by ID: "
                + original.equals(duplicateId));

        System.out.println("Hash codes equal: "
                + (original.hashCode() == duplicateId.hashCode()));

        Set<Employee> employeeSet = new HashSet<>();
        employeeSet.add(original);
        employeeSet.add(duplicateId);

        System.out.println("HashSet size for same ID: "
                + employeeSet.size());

        // 11. Demonstrate the bug: equals() without hashCode().
        System.out.println("\n=== MISSING HASHCODE BUG DEMO ===");

        BrokenEmployee broken1 = new BrokenEmployee(201);
        BrokenEmployee broken2 = new BrokenEmployee(201);

        System.out.println("Broken employees equal: "
                + broken1.equals(broken2));

        Set<BrokenEmployee> brokenSet = new HashSet<>();
        brokenSet.add(broken1);
        brokenSet.add(broken2);

        System.out.println("HashSet size without hashCode(): "
                + brokenSet.size());

        System.out.println(
                "Expected problem: equal objects may occupy separate hash buckets.");

        // 12. Compare with the corrected Employee implementation.
        System.out.println("\n=== HASHCODE BUG FIX ===");

        Employee fixed1 =
                new Employee(201, "Anu", "Engineering", 70000);

        Employee fixed2 =
                new Employee(201, "Another Name", "HR", 60000);

        Set<Employee> fixedSet = new HashSet<>();
        fixedSet.add(fixed1);
        fixedSet.add(fixed2);

        System.out.println("Fixed employees equal: "
                + fixed1.equals(fixed2));

        System.out.println("Fixed hash codes equal: "
                + (fixed1.hashCode() == fixed2.hashCode()));

        System.out.println("HashSet size after fix: "
                + fixedSet.size());

        System.out.println("\nTotal employees: " + employees.size());
    }

    // Intentionally incorrect class for demonstration only.
    // equals() is overridden, but hashCode() is deliberately omitted.
    static class BrokenEmployee {

        private final int id;

        BrokenEmployee(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }

            if (!(object instanceof BrokenEmployee other)) {
                return false;
            }

            return id == other.id;
        }

        // Intentionally missing hashCode().
    }
}
