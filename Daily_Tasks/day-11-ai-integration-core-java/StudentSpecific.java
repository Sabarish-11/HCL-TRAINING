import java.util.Objects;

public class StudentSpecific {

    private final int id;
    private final String name;
    private final int age;

    public StudentSpecific(int id, String name, int age) {
        validateId(id);
        validateName(name);
        validateAge(age);

        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    private static void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Student ID must be positive");
        }
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Student name cannot be null or blank");
        }
    }

    private static void validateAge(int age) {
        if (age < 0 || age > 120) {
            throw new IllegalArgumentException(
                    "Student age must be between 0 and 120");
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof StudentSpecific)) {
            return false;
        }

        StudentSpecific other = (StudentSpecific) object;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "StudentSpecific{id=" + id
                + ", name='" + name + '\''
                + ", age=" + age + '}';
    }
}