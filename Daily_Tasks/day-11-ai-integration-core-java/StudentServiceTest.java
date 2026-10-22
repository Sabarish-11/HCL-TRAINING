import java.util.List;

public class StudentServiceTest {
    public static void main(String[] args) {
        StudentService service = new StudentService();

        StudentSpecific student = new StudentSpecific(1, "Sabarish", 21);
        service.addStudent(student);

        // Verify successful lookup
        if (service.findStudentById(1).isEmpty()) {
            throw new AssertionError("Student lookup failed");
        }

        // Verify duplicate IDs are rejected
        try {
            service.addStudent(new StudentSpecific(1, "Another Student", 22));
            throw new AssertionError("Duplicate ID was accepted");
        } catch (IllegalArgumentException expected) {
            System.out.println("PASS: Duplicate ID rejected");
        }

        // Verify returned list cannot be modified
        List<StudentSpecific> students = service.getAllStudents();
        try {
            students.clear();
            throw new AssertionError("Returned list was modifiable");
        } catch (UnsupportedOperationException expected) {
            System.out.println("PASS: Returned list is unmodifiable");
        }

        // Verify invalid IDs are rejected
        try {
            service.findStudentById(0);
            throw new AssertionError("Invalid ID was accepted");
        } catch (IllegalArgumentException expected) {
            System.out.println("PASS: Invalid ID rejected");
        }

        System.out.println("All checks passed.");
    }
}