import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PersistenceManager {
    private static String PATH = "src\\main\\resources\\tasks.CSV";
    private Scanner scanner;
    private PrintWriter writer;

    PersistenceManager() {
        try {
            writer = new PrintWriter(new FileWriter(PATH, true));
            scanner = new Scanner(new FileInputStream(PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        if (scanner.hasNextLine())
            scanner.nextLine();

        while (scanner.hasNextLine()) {
            String[] tokens = scanner.nextLine().trim().split(",");

            int id = Integer.parseInt(tokens[0]);
            String title = tokens[1];
            String description = tokens[2];
            LocalDateTime dueDate = LocalDateTime.parse(tokens[3]);
            Priority priority = Priority.valueOf(tokens[4]);
            Category category = Category.valueOf(tokens[5]);
            boolean isCompleted = Boolean.parseBoolean(tokens[6]);

            Task task = new Task(
                    id,
                    title,
                    description,
                    dueDate,
                    priority,
                    category);
            task.setComplete(isCompleted);
            tasks.add(task);
        }
        return tasks;
    }

    public void save(List<Task> tasks) {
        try(Writer writer1 = Files.newBufferedWriter(Paths.get(PATH) , StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        writer.println("id,title,description,dueDate,priority,category,isComplete");
        for (Task task : tasks) {
            writer.printf("%d,%s,%s,%s,%s,%s,%s%n",
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getDate(),
                    task.getPriority(),
                    task.getCategory(),
                    task.isComplete());
        }
        writer.flush();
    }
}
