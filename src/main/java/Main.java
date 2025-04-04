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
                case 1 -> System.out.println("Dummy operation");
                case 2 -> System.out.println("Dummy operation");
                case 3 -> System.out.println("Dummy operation");
                case 4 -> System.out.println("Dummy operation");
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
}
