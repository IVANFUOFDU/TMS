import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);
    private final static TaskManager manager = new TaskManager();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = getIntInput("Choose: ");

            switch (choice) {
                case 1 -> addTask();
                case 2 -> listTask();
                case 3 -> updateTask();
                case 4 -> deleteTask();
                case 5 -> completeTask();
                case 6 -> running = false;
                default -> System.out.println("Invalid choice. Retry.");
            }
        }
        manager.save();
        System.out.println("Exiting TMS.");
    }

    private static void completeTask() {
        listTask();
        if (manager.getAll().isEmpty())
            return;

        while (true) {
            System.out.println("\n---> What task you completed? <---");
            int id = getIntInput("Enter id: ");
            Optional<Task> taskOptional = manager.getById(id);
            if (taskOptional.isEmpty()) {
                System.out.println("Task do not exist, choose other id: ");
                continue;
            }
            Task task = taskOptional.get();
            manager.update(
                    id,
                    task.getTitle(),
                    task.getDescription(),
                    task.getDate(),
                    task.getPriority(),
                    task.getCategory(),
                    true);
            System.out.println("Task set completed successfully.");
            return;
        }
    }

    private static void printMenu() {
        System.out.println("\n---> Task Manager System <---");
        System.out.println("\t 1. Add Task");
        System.out.println("\t 2. List Tasks");
        System.out.println("\t 3. Update Task");
        System.out.println("\t 4. Delete Task");
        System.out.println("\t 5. Set as completed");
        System.out.println("\t 6. Exit");
    }

    public static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException exception) {
                System.out.println("Invalid input. Retry.");
            }
        }
    }

    public static String getStringInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine().trim();
    }

    public static LocalDateTime getDateTimeInput(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty())
                    return null;
                return LocalDateTime.parse(input);
            } catch (Exception e) {
                System.out.println("Invalid date format. Use YYYY-MM-DDTHH:mm:ss");
            }
        }
    }

    public static Priority getPriorityInput(String prompt) {
        while (true)
            try {
                System.out.println(prompt);
                String input = scanner.nextLine().trim().toUpperCase();
                if (input.isEmpty())
                    return Priority.MEDIUM;
                return Priority.valueOf(input);
            } catch (IllegalArgumentException exception) {
                System.out.println("invalid priority. Choose from LOW/MEDIUM/HIGH");
            }
    }


    public static Category getCategoryInput(String prompt) {
        while (true)
            try {
                System.out.println(prompt);
                String input = scanner.nextLine().trim().toUpperCase();
                if (input.isEmpty())
                    return Category.PERSONAL;
                return Category.valueOf(input);
            } catch (IllegalArgumentException exception) {
                System.out.println("Invalid category. Choose from WORK/PERSONAL");
            }
    }


    public static void addTask() {
        System.out.println("\n---> Add Task <---");
        String title = getStringInput("Enter title: ");
        String description = getStringInput("Enter description: ");
        LocalDateTime dueDate = getDateTimeInput("Enter due date (YYYY-MM-DDTHH:mm:ss)");
        Priority priority = getPriorityInput("Enter priority (LOW/MEDIUM/HIGH) [default: MEDIUM]");
        Category category = getCategoryInput(" Enter category (WORK/PERSONAL) [default: PERSONAL]");

        manager.add(title, description, dueDate, priority, category);
        System.out.println("Task added successfully.");
    }

    public static void listTask() {
        List<Task> tasks = manager.getAll();
        System.out.println("\n---> List Tasks <---");
        if (tasks.isEmpty()) {
            System.out.println("There are not tasks.");
            return;
        }

        for (Task task : tasks) {
            System.out.printf(
                    "[ID: %d] %s - %s | Due: %s | Category: %s | Priority: %s | Status: %s%n",
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getDate(),
                    task.getCategory(),
                    task.getPriority(),
                    task.isComplete() ? "Complete" : "Incomplete"
            );
        }
    }

    public static void updateTask() {
        int id = getIntInput("Enter id to update: ");
        Optional<Task> taskOptional = manager.getById(id);

        if (taskOptional.isEmpty()) {
            System.out.println("Task not found.");
            return;
        }

        System.out.println("\n---> Update Task <---");
        String newTitle = getStringInput("Enter new title (leave blank to keep current): ");
        String newDescription = getStringInput("Enter new description (leave blank to keep current): ");
        LocalDateTime newDueDate = getDateTimeInput("Enter new due date (YYYY-MM-DDTHH:mm:ss) (leave blank to keep current): ");
        Priority newPriority = getPriorityInput("Enter new priority (LOW/MEDIUM/HIGH (leave blank to keep current): ");
        Category newCategory = getCategoryInput("Enter new category (WORK/PERSONAL) (leave blank to keep current): ");

        Task task = taskOptional.get();
        manager.update(
                id,
                newTitle.isEmpty() ? task.getTitle() : newTitle,
                newDescription.isEmpty() ? task.getDescription() : newDescription,
                newDueDate == null ? task.getDate() : newDueDate,
                newPriority == null ? task.getPriority() : newPriority,
                newCategory == null ? task.getCategory() : newCategory,
                false
        );
        System.out.println("Task updated successfully");
    }

    public static void deleteTask() {
        System.out.println("\n---> Delete Task <---");
        int id = getIntInput("Enter ID to delete: ");
        boolean deleted = manager.delete(id);
        System.out.println(deleted ? "Deleted successfully" : "Task not found");
    }
}
