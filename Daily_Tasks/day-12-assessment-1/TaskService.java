
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    public Task createTask(String title) {
        Task task = new Task(nextId, title);
        tasks.add(task);
        nextId++;
        return task;
    }

    public List<Task> getAllTasks() {
        return List.copyOf(tasks);
    }

    public Optional<Task> findTaskById(int id) {
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst();
    }

    public void moveTask(int id, String newStatus) {
        Task task = findTaskById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Task not found: " + id));

        task.moveTo(newStatus);
    }
}
