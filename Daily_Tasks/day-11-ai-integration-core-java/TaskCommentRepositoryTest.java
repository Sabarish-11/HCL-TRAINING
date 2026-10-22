import java.time.LocalDateTime;
import java.util.List;

public class TaskCommentRepositoryTest {

    public static void main(String[] args) {
        TaskCommentRepository repository =
                new InMemoryTaskCommentRepository();

        TaskComment first = new TaskComment(
                1, 100, 10, "First comment",
                LocalDateTime.now());

        TaskComment second = new TaskComment(
                2, 100, 11, "Second comment",
                LocalDateTime.now());

        repository.save(first);
        repository.save(second);

        check(repository.findById(1).isPresent(),
                "Find saved comment");

        check(repository.findByTaskId(100).size() == 2,
                "Find comments by task");

        check(repository.findByTaskId(999).isEmpty(),
                "Missing task returns empty list");

        check(repository.deleteById(1),
                "Delete existing comment");

        check(repository.findById(1).isEmpty(),
                "Deleted comment is no longer found");

        expectIllegalArgument(
                () -> repository.save(null),
                "Null comment rejected");

        expectIllegalArgument(
                () -> repository.findById(0),
                "Invalid ID rejected");

        List<TaskComment> results =
                repository.findByTaskId(100);

        expectUnsupportedOperation(
                () -> results.add(first),
                "Returned task-comment list is unmodifiable");

        System.out.println("All repository checks passed.");
    }

    private static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError("FAIL: " + description);
        }
        System.out.println("PASS: " + description);
    }

    private static void expectIllegalArgument(
            Runnable action, String description) {
        try {
            action.run();
            throw new AssertionError("FAIL: " + description);
        } catch (IllegalArgumentException expected) {
            System.out.println("PASS: " + description);
        }
    }

    private static void expectUnsupportedOperation(
            Runnable action, String description) {
        try {
            action.run();
            throw new AssertionError("FAIL: " + description);
        } catch (UnsupportedOperationException expected) {
            System.out.println("PASS: " + description);
        }
    }
}