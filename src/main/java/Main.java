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
                case 5 -> running = false;
                default -> System.out.println("Invalid choice. Retry.");
            }
        }
        System.out.println("Exiting TMS.");
    }

    private static void printMenu() {
        System.out.println("\n---> Task Manager System <---");
        System.out.println("\t 1. Add Task");
        System.out.println("\t 2. List Tasks");
        System.out.println("\t 3. Update Task");
        System.out.println("\t 4. Delete Task");
        System.out.println("\t 5. Exit");
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

    public static void addTask() {
        System.out.println("\n---> Add Task <---");
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
    }

    public static void deleteTask() {
        System.out.println("\n---> Delete Task <---");
        int id = getIntInput("Enter ID to delete: ");
        boolean deleted = manager.delete(id);
        System.out.println(deleted ? "Deleted successfully" : "Task not found");
    }
}
