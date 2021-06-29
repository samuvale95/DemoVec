package vec.demo.todo;

public class Task {
    private String name;
    private String description;
    private Priority priority;
    private Boolean active;

    public Task(String name, String description){
        this.name = name;
        this.description = description;
        this.priority = Priority.LOW;
        this.active = true;
    }

    public Task(String name, String description, Priority priority){
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.active = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Boolean getStatus() {
        return active;
    }

    public void toggleStatus() {
        this.active = !this.active;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", active=" + active +
                '}';
    }
}
