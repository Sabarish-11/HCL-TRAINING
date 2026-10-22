
public class TaskServiceTest {
    public static void main(String[] args) {
        TaskService service = new TaskService();

        // Test 1: Create task and check default status
        Task first = service.createTask("Test task");
        assert first.getId() == 1;
        assert first.getStatus().equals("TODO");

        // Test 2: IDs increase
        Task second = service.createTask("Second task");
        assert second.getId() == 2;

        // Test 3: Move task
        service.moveTask(1, "IN_PROGRESS");
        assert service.findTaskById(1).orElseThrow()
                .getStatus().equals("IN_PROGRESS");

        // Test 4: Reject blank titles
        try {
            service.createTask("   ");
            throw new AssertionError("Blank title was accepted");
        } catch (IllegalArgumentException expected) {
            // Expected
        }

        // Test 5: Reject invalid status
        try {
            service.moveTask(1, "INVALID");
            throw new AssertionError("Invalid status was accepted");
        } catch (IllegalArgumentException expected) {
            // Expected
        }

        // Test 6: Reject missing task ID
        try {
            service.moveTask(999, "DONE");
            throw new AssertionError("Missing task was accepted");
        } catch (IllegalArgumentException expected) {
            // Expected
        }

        // Test 7: Returned task list cannot be modified
        try {
            service.getAllTasks().clear();
            throw new AssertionError("Task list was modifiable");
        } catch (UnsupportedOperationException expected) {
            // Expected
        }

        System.out.println("All 7 TaskService tests passed!");
    }
}
