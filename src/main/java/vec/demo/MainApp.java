package vec.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vec.demo.todo.Todo;
import vec.demo.todo.Priority;
import vec.demo.todo.Task;

public class MainApp extends Application {
    Todo todo = new Todo();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Todo List With Steroids");

        TableView<Task> tableView = new TableView<>();
        tableView.setId("table");
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setOnMouseClicked(e->tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE));


        TableColumn<Task, String> colName = new TableColumn<>("Name");
        TableColumn<Task, String> colDescr = new TableColumn<>("Description");
        TableColumn<Task, Priority> colPriority = new TableColumn<>("Priority");

        tableView.getColumns().addAll(colName, colDescr, colPriority);

        tableView.getItems().addAll(todo.getAll());

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescr.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPriority.setCellValueFactory(new PropertyValueFactory<>("priority"));

        VBox vBox = new VBox();
        InsertTaskForm insertTaskForm = new InsertTaskForm(tableView, todo);
        ModifyTaskForm modifyTaskForm = new ModifyTaskForm(tableView, vBox, insertTaskForm);

        vBox.getChildren().addAll(tableView, insertTaskForm);

        tableView.setRowFactory( tv -> {
            TableRow<Task> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    tableView.setDisable(true);
                    Task t = row.getItem();
                    modifyTaskForm.getInputField().getName().setText(t.getName());
                    modifyTaskForm.getInputField().getDesc().setText(t.getDescription());
                    modifyTaskForm.getInputField().getPriority().setValue(t.getPriority());
                    vBox.getChildren().remove(insertTaskForm);
                    vBox.getChildren().add(modifyTaskForm);
                }
            });
            return row ;
        });

        Scene scene = new Scene(vBox);
        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(650);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
