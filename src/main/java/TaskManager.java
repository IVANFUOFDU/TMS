import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskManager {
    private List<Task> taskList;
    private int nextId;
    private PersistenceManager persistenceManager;

    public TaskManager() {
        persistenceManager = new PersistenceManager();
        taskList = persistenceManager.loadTasks();
        if (!taskList.isEmpty())
            nextId = taskList.getLast().getId() + 1;
        else
            nextId = 0;
    }

    // CREATE
    public void add(String title, String description, LocalDateTime date, Priority priority, Category category) {
        Task newTask = new Task(nextId++, title, description, date, priority, category);
        taskList.add(newTask);
    }

    // READ
    public Optional<Task> getById(int id) {
        return taskList.stream()
                .filter(task -> task.getId() == id)
                .findFirst();
    }

    // READ ALL
    public List<Task> getAll() {
        return taskList;
    }

    // UPDATE
    public boolean update(int id, String newTitle, String newDescription, LocalDateTime newDate, Priority newPriority, Category newCategory, boolean isCompleted) {
        Optional<Task> taskOptional = getById(id);

        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setTitle(newTitle);
            task.setDescription(newDescription);
            task.setDate(newDate);
            task.setPriority(newPriority);
            task.setCategory(newCategory);
            task.setComplete(isCompleted);
            return true;
        }
        return false;
    }

    // DELETE
    public boolean delete(int id) {
        return taskList.removeIf(task -> task.getId() == id);
    }

    public void save() {
        persistenceManager.save(taskList);
    }
}
