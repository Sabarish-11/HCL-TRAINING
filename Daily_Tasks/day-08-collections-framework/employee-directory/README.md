Day 8 — Collections Framework: Part 2

Objective

Practice Java Maps, natural ordering, custom Comparators, and the equals() / hashCode() contract.

Topics Covered

HashMap for employee lookup by ID

LinkedHashMap for preserving insertion order

TreeMap for sorted department reports

Map.merge() for department frequency counting

Comparable<Employee> for natural ordering by employee ID

Comparator<Employee> for department and salary sorting

Comparator chaining using comparing(), reversed(), and thenComparing()

Sorting map entries by key and value

The equals() / hashCode() contract

Standalone Exercise

The employee-directory folder contains:

Employee.java — employee model, natural ordering, and correct equality/hash code

SalaryComparator.java — descending salary order, followed by name and ID

DepartmentComparator.java — department, name, and ID ordering

EmployeeDirectory.java — runnable demonstrations of Maps, sorting, and the hash code bug

Hash Code Bug Demonstration

The nested BrokenEmployee class overrides equals() but deliberately omits hashCode(). The demonstration shows how equal objects may be stored separately in a HashSet.

The corrected Employee class overrides both methods consistently, so employees with the same ID are treated as equal set elements.

Rule: If two objects are equal according to equals(), they must return the same hash code.

Run the Exercise

From the employee-directory folder:

javac Employee.java SalaryComparator.java DepartmentComparator.java EmployeeDirectory.java
java EmployeeDirectory

Evidence

screenshots/01-employee-directory-output.png

screenshots/02-hashcode-bug-and-fix.png

employee-directory/output.txt

Validation

The program compiled and ran successfully. The output confirmed employee lookup, department counts, sorted reports, top-five salary ordering, and the equality/hash code demonstration.