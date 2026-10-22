
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentService {

    private final List<StudentSpecific> students = new ArrayList<>();

    public void addStudent(StudentSpecific student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        if (findStudentById(student.getId()).isPresent()) {
            throw new IllegalArgumentException(
                    "Student ID already exists: " + student.getId());
        }

        students.add(student);
    }

    public List<StudentSpecific> getAllStudents() {
        return List.copyOf(students);
    }

    public Optional<StudentSpecific> findStudentById(int id) {
        validateId(id);

        for (StudentSpecific student : students) {
            if (student.getId() == id) {
                return Optional.of(student);
            }
        }

        return Optional.empty();
    }

    public boolean removeStudentById(int id) {
        validateId(id);

        return students.removeIf(student -> student.getId() == id);
    }

    private static void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Student ID must be positive");
        }
    }
}
