import java.time.LocalDateTime;
import java.util.Objects;

public final class TaskComment {

    private final long id;
    private final long taskId;
    private final long authorId;
    private final String content;
    private final LocalDateTime createdAt;

    public TaskComment(long id, long taskId, long authorId,
                       String content, LocalDateTime createdAt) {
        if (id <= 0 || taskId <= 0 || authorId <= 0) {
            throw new IllegalArgumentException(
                    "IDs must be positive");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException(
                    "Comment content cannot be blank");
        }

        this.id = id;
        this.taskId = taskId;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = Objects.requireNonNull(
                createdAt, "createdAt cannot be null");
    }

    public long getId() {
        return id;
    }

    public long getTaskId() {
        return taskId;
    }

    public long getAuthorId() {
        return authorId;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "TaskComment{id=" + id
                + ", taskId=" + taskId
                + ", authorId=" + authorId
                + ", content='" + content + '\''
                + ", createdAt=" + createdAt + '}';
    }
}