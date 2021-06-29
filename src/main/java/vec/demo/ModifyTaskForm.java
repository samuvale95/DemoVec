package vec.demo;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vec.demo.todo.Task;

public class ModifyTaskForm extends HBox {
    private final Button btnSave;
    private final Button btnCancel;
    private final FormInputField inputField;

    public ModifyTaskForm(TableView<Task> tableView, VBox vBox, InsertTaskForm insertTaskForm) {

        this.btnSave = new Button("Save");
        this.btnSave.setDisable(true);

        this.btnCancel = new Button("Cancel");
        this.inputField = new FormInputField();

        this.setPadding(new Insets(10,10,10,10));
        this.setSpacing(10);
        this.getChildren().addAll(inputField, btnSave, btnCancel);

        inputField.getName().setOnKeyTyped(e-> disableOrEnableSaveButton(tableView));

        inputField.getDesc().setOnKeyTyped(e-> disableOrEnableSaveButton(tableView));

        inputField.getPriority().setOnKeyTyped(e-> disableOrEnableSaveButton(tableView));

        btnCancel.setOnAction(e->{
            tableView.setDisable(false);
            inputField.clearAll();
            vBox.getChildren().remove(this);
            vBox.getChildren().add(insertTaskForm);
        });

        btnSave.setOnAction(e->{
            tableView.setDisable(false);
            Task task = tableView.getSelectionModel().getSelectedItem();
            task.setName(inputField.getName().getText());
            task.setDescription(inputField.getDesc().getText());
            task.setPriority(inputField.getPriority().getValue());
            tableView.refresh();

            vBox.getChildren().remove(this);
            inputField.clearAll();
            vBox.getChildren().add(insertTaskForm);
        });
    }

    private void disableOrEnableSaveButton(TableView<Task> tableView) {
        Task t = tableView.getSelectionModel().getSelectedItem();
        btnSave.setDisable(
                inputField.getName().getText().equals(t.getName()) &&
                inputField.getDesc().getText().equals(t.getDescription()) &&
                inputField.getPriority().getValue() == t.getPriority()
        );
    }

    public FormInputField getInputField(){
        return inputField;
    }

}
