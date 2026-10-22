import java.util.List;
import java.util.Optional;

public interface TaskCommentRepository {

    TaskComment save(TaskComment comment);

    Optional<TaskComment> findById(long id);

    List<TaskComment> findByTaskId(long taskId);

    boolean deleteById(long id);
}