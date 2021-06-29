package vec.demo;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import vec.demo.todo.Priority;

public class FormInputField extends HBox {

    private final TextField name;
    private final TextField desc;
    private final PriorityChoiceBox priority;

    public FormInputField() {
        this.name = new TextField();
        this.name.setPromptText("Name");
        this.name.setId("inputName");

        this.desc = new TextField();
        this.desc.setPromptText("Description");
        this.desc.setId("inputDesc");


        this.priority = new PriorityChoiceBox();
        this.priority.setId("inputPriority");

        this.getChildren().add(name);
        this.getChildren().add(desc);
        this.getChildren().add(priority);

        this.setSpacing(10);
    }

    public TextField getName() {
        return name;
    }

    public TextField getDesc() {
        return desc;
    }

    public PriorityChoiceBox getPriority() {
        return priority;
    }

    public void clearAll(){
        this.name.clear();
        this.desc.clear();
        this.priority.setValue(Priority.LOW);
    }
}
