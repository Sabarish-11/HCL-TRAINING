import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryTaskCommentRepository
        implements TaskCommentRepository {

    private final List<TaskComment> comments = new ArrayList<>();

    @Override
    public TaskComment save(TaskComment comment) {
        if (comment == null) {
            throw new IllegalArgumentException(
                    "Comment cannot be null");
        }

        for (int i = 0; i < comments.size(); i++) {
            if (comments.get(i).getId() == comment.getId()) {
                comments.set(i, comment);
                return comment;
            }
        }

        comments.add(comment);
        return comment;
    }

    @Override
    public Optional<TaskComment> findById(long id) {
        validateId(id);

        return comments.stream()
                .filter(comment -> comment.getId() == id)
                .findFirst();
    }

    @Override
    public List<TaskComment> findByTaskId(long taskId) {
        validateId(taskId);

        return comments.stream()
                .filter(comment -> comment.getTaskId() == taskId)
                .toList();
    }

    @Override
    public boolean deleteById(long id) {
        validateId(id);
        return comments.removeIf(comment -> comment.getId() == id);
    }

    private static void validateId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "ID must be positive");
        }
    }
}