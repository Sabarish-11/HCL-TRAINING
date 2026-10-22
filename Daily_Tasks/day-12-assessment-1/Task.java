
public class Task {
    private final int id;
    private final String title;
    private String status;

    public Task(int id, String title) {
        if (id <= 0) {
            throw new IllegalArgumentException("Task ID must be positive.");
        }

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title cannot be empty.");
        }

        this.id = id;
        this.title = title;
        this.status = "TODO";
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public void moveTo(String newStatus) {
        if (newStatus == null ||
            !(newStatus.equals("TODO") ||
              newStatus.equals("IN_PROGRESS") ||
              newStatus.equals("DONE"))) {
            throw new IllegalArgumentException("Invalid task status.");
        }

        this.status = newStatus;
    }

    @Override
    public String toString() {
        return "Task{id=" + id
                + ", title='" + title + '\''
                + ", status='" + status + "'}";
    }
}
