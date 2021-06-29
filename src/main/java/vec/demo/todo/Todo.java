package vec.demo.todo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vec.demo.todo.Task;

import java.util.ArrayList;

public class Todo {
    private final ObservableList<Task> tasks = FXCollections.observableArrayList();

    public void add(Task task){
        this.tasks.add(task);
    }

    public void remove(Task task){
        this.tasks.remove(task);
    }

    public Task get(Task task){
        return this.tasks.get(this.tasks.indexOf(task));
    }

    public ObservableList<Task> getAll(){
        return tasks;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "tasks=" + tasks +
                '}';
    }
}
