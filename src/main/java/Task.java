import java.time.LocalDateTime;

public class Task {
    private final int id;
    private String title;
    private String description;
    private LocalDateTime date;
    private Priority priority;
    private Category category;
    private boolean isComplete;

    public Task(int id, String title, String description, LocalDateTime date, Priority priority, Category category, boolean isComplete) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.priority = priority;
        this.category = category;
        this.isComplete = isComplete;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }
}
