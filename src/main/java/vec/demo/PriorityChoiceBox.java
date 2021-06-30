package vec.demo;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import vec.demo.todo.Priority;

public class PriorityChoiceBox extends ComboBox<Priority> {

    public PriorityChoiceBox() {
        this.getItems().addAll(
                Priority.LOW,
                Priority.MEDIUM,
                Priority.HIGH,
                Priority.CRITIC
        );
        this.setValue(Priority.LOW);
    }
}