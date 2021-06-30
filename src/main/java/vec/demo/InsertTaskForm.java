package vec.demo;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import vec.demo.todo.Task;
import vec.demo.todo.Todo;

public class InsertTaskForm extends HBox {
    private final Button btnAdd;
    private final Button btnDone;
    private final FormInputField inputField;

    public InsertTaskForm(TableView<Task> tableView, Todo todo) {
        this.btnAdd = new Button("Add");
        this.btnAdd.setId("add");
        this.btnDone = new Button("Done");
        this.btnDone.setId("done");
        this.inputField = new FormInputField();

        this.getChildren().addAll(inputField, btnAdd, btnDone);

        this.setPadding(new Insets(10,10,10,10));
        this.setSpacing(10);

        btnAdd.setDisable(true);
        inputField.getName().setOnKeyTyped(e->btnAdd.setDisable(inputField.getName().getText().isEmpty()));

        btnAdd.setOnAction(e -> {
            Task task = new Task(
                    inputField.getName().getText(),
                    inputField.getDesc().getText(),
                    inputField.getPriority().getValue()
            );

            todo.add(task);
            tableView.getItems().add(task);

            inputField.clearAll();
        });

        btnDone.setDisable(true);
        tableView.setOnMouseClicked(e->btnDone.setDisable(tableView.getSelectionModel().getSelectedItems().isEmpty()));
        btnDone.setOnAction(e->{
            ObservableList<Task> tasks = tableView.getItems();
            ObservableList<Task> selectedTasks = tableView.getSelectionModel().getSelectedItems();
            tasks.removeAll(selectedTasks);
            btnDone.setDisable(tasks.isEmpty());
        });
    }
}
