package vec.demo;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.api.FxToolkit.registerPrimaryStage;
import static org.testfx.assertions.api.Assertions.assertThat;

import javafx.stage.Stage;
import vec.demo.todo.Priority;
import vec.demo.todo.Task;

import java.util.concurrent.TimeoutException;

@ExtendWith(ApplicationExtension.class)
class InsertTaskFormTest {

    private final String ADD_BUTTON = "#add";
    private final String DONE_BUTTON = "#done";
    private final String NAME_FIELD = "#inputName";
    private final String DESC_FIELD = "#inputDesc";
    private final String PRIORITY_CHOICE = "#inputPriority";
    private final String TABLE = "#table";

    @BeforeAll
    public static void setUpClass(){
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
    }

    @Start
    public void start(Stage stage) {
        try {
            new MainApp().start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setup() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
    }

    @AfterEach
    public void tearDown() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    @Test
    public void ActivateDeactivateAddButtonWithNameField(FxRobot robot){
        String testWord = "Test";

        verifyThat(ADD_BUTTON, Node::isDisable);
        verifyThat(DONE_BUTTON, Node::isDisable);

        robot.clickOn(NAME_FIELD).write(testWord);
        verifyThat(ADD_BUTTON, node -> !node.isDisabled());
        verifyThat(DONE_BUTTON, Node::isDisable);
    }

    @Test
    public void DeactiveAddButtonWithDescriptionField(FxRobot robot) {
        verifyThat(ADD_BUTTON, Node::isDisable);
        verifyThat(DONE_BUTTON, Node::isDisable);

        robot.clickOn(DESC_FIELD).write("Test");
        verifyThat(ADD_BUTTON, Node::isDisable);
        verifyThat(DONE_BUTTON, Node::isDisable);

        robot.clickOn(DESC_FIELD).write("");
        verifyThat(ADD_BUTTON, Node::isDisable);
        verifyThat(DONE_BUTTON, Node::isDisable);
    }

    @Test
    public void shouldClearTextFields(FxRobot robot) {
        robot.clickOn(NAME_FIELD).write("Test");
        robot.clickOn(DESC_FIELD).write("Test Desc");

        robot.clickOn(PRIORITY_CHOICE);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);

        robot.clickOn(ADD_BUTTON);

        verifyThat(NAME_FIELD, (TextField t) -> t.getText().isEmpty());
        verifyThat(DESC_FIELD, (TextField t) -> t.getText().isEmpty());
        verifyThat(PRIORITY_CHOICE, (PriorityChoiceBox t) -> t.getValue() == Priority.LOW);
    }

    @Test
    public void addTaskToTable(FxRobot robot) {
        String testName = "Test";
        String testDesc = "Test Desc";
        Priority testPriority = Priority.MEDIUM;

        robot.clickOn(NAME_FIELD).write(testName);
        robot.clickOn(DESC_FIELD).write(testDesc);

        //Set priority MEDIUM
        robot.clickOn(PRIORITY_CHOICE);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);

        robot.clickOn(ADD_BUTTON);

        verifyThat(TABLE, (TableView<Task> t) -> t.getItems().size() == 1);

        Task t = (Task) robot.lookup(TABLE).queryTableView().getItems().get(0);

        assertThat(t.getName()).isEqualTo(testName);
        assertThat(t.getDescription()).isEqualTo(testDesc);
        assertThat(t.getPriority()).isEqualTo(testPriority);
    }

    private void clearField(FxRobot robot, String fieldID) {
        robot.clickOn(fieldID)
                .press(KeyCode.COMMAND)
                .press(KeyCode.A)
                .release(KeyCode.A)
                .release(KeyCode.COMMAND)
                .type(KeyCode.BACK_SPACE);
    }

}