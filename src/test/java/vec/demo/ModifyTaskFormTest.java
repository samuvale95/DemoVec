package vec.demo;

import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxRobotException;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.*;
import vec.demo.todo.Priority;
import vec.demo.todo.Task;
import java.util.concurrent.TimeoutException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.api.FxToolkit.registerPrimaryStage;
import static org.testfx.assertions.api.Assertions.assertThat;


@ExtendWith(ApplicationExtension.class)
public class ModifyTaskFormTest{

    private final String ADD_BUTTON = "#add";
    private final String DONE_BUTTON = "#done";
    private final String SAVE_BUTTON = "#save";
    private final String CANCEL_BUTTON = "#cancel";
    private final String NAME_FIELD = "#inputName";
    private final String DESC_FIELD = "#inputDesc";
    private final String PRIORITY_CHOICE = "#inputPriority";
    private final String TABLE = "#table";
    private final String TEST_NAME = "Test";
    private final String TEST_DESC = "Test Desc";
    private final Priority TEST_PRIORITY = Priority.MEDIUM;


    @BeforeAll
    public static void setUpClass() {
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

    public void addTask(FxRobot robot) {
        robot.clickOn(NAME_FIELD).write(TEST_NAME);
        robot.clickOn(DESC_FIELD).write(TEST_DESC);

        //Select priority MEDIUM
        robot.clickOn(PRIORITY_CHOICE);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);

        robot.clickOn(ADD_BUTTON);
    }

    @Test
    public void modifyFormShouldBeFilled(FxRobot robot) {
        addTask(robot);

        TableRow<Task> row = robot.lookup(TABLE).lookup(".table-row-cell").nth(0).query();
        robot.doubleClickOn(row);

        verifyThat(NAME_FIELD, (TextField t) -> t.getText().equals(TEST_NAME));
        verifyThat(DESC_FIELD, (TextField t) -> t.getText().equals(TEST_DESC));
        verifyThat(PRIORITY_CHOICE, (ComboBox<Priority> c) -> c.getValue() == TEST_PRIORITY);
    }

    @Test
    public void saveModifiedValues(FxRobot robot) {
        addTask(robot);
        TableRow<Task> row = robot.lookup(TABLE).lookup(".table-row-cell").nth(0).query();
        robot.doubleClickOn(row);

        String modifyTestName = "Test2";
        String modifyTestDesc = "Test desc 2";
        Priority modifyPriority = Priority.CRITIC;

        robot.clickOn(NAME_FIELD);
        clearField(robot, NAME_FIELD);
        robot.clickOn(NAME_FIELD).write(modifyTestName);

        robot.clickOn(DESC_FIELD);
        clearField(robot, DESC_FIELD);
        robot.clickOn(DESC_FIELD).write(modifyTestDesc);

        robot.clickOn(PRIORITY_CHOICE);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);

        robot.clickOn(SAVE_BUTTON);

        verifyThat(TABLE, node -> !node.isDisabled());

        Task t = (Task) robot.lookup(TABLE).queryTableView().getItems().get(0);

        assertThat(t.getName()).isEqualTo(modifyTestName);
        assertThat(t.getDescription()).isEqualTo(modifyTestDesc);
        assertThat(t.getPriority()).isEqualTo(modifyPriority);

    }

    @Test
    public void onDoubleClickOverTableRowShouldBeChangeForm(FxRobot robot) {
        addTask(robot);
        TableRow<Task> row = robot.lookup(TABLE).lookup(".table-row-cell").nth(0).query();
        robot.doubleClickOn(row);

        assertThatThrownBy(() -> robot.clickOn(ADD_BUTTON)).isInstanceOf(FxRobotException.class);
        assertThatThrownBy(() -> robot.clickOn(DONE_BUTTON)).isInstanceOf(FxRobotException.class);
    }

    @Test
    public void cancelButtonShouldChangeForm(FxRobot robot) {
        addTask(robot);
        TableRow<Task> row = robot.lookup(TABLE).lookup(".table-row-cell").nth(0).query();
        robot.doubleClickOn(row);

        robot.clickOn(CANCEL_BUTTON);

        verifyThat(TABLE, node -> !node.isDisabled());

        assertThatThrownBy(() -> robot.clickOn(CANCEL_BUTTON)).isInstanceOf(FxRobotException.class);
        assertThatThrownBy(() -> robot.clickOn(SAVE_BUTTON)).isInstanceOf(FxRobotException.class);
    }

    @Test
    public void saveButtonShouldDisable(FxRobot robot) {
        addTask(robot);
        TableRow<Task> row = robot.lookup(TABLE).lookup(".table-row-cell").nth(0).query();
        robot.doubleClickOn(row);

        verifyThat(SAVE_BUTTON, Node::isDisabled);

        clearField(robot, NAME_FIELD);
        robot.clickOn(NAME_FIELD).write("Test modified");
        verifyThat(SAVE_BUTTON, node -> !node.isDisabled());
        //Reset Field Name
        clearField(robot, NAME_FIELD);
        robot.clickOn(NAME_FIELD).write(TEST_NAME);

        clearField(robot, DESC_FIELD);
        robot.clickOn(DESC_FIELD).write("Test modified");
        verifyThat(SAVE_BUTTON, node -> !node.isDisabled());
        //Reset Field Desc
        clearField(robot, DESC_FIELD);
        robot.clickOn(DESC_FIELD).write(TEST_DESC);

        robot.clickOn(PRIORITY_CHOICE);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        verifyThat(SAVE_BUTTON, node -> !node.isDisabled());
    }

    @Test
    public void tableViewShouldDisabledOnModifyView(FxRobot robot){
        addTask(robot);
        TableRow<Task> row = robot.lookup(TABLE).lookup(".table-row-cell").nth(0).query();
        robot.doubleClickOn(row);

        verifyThat(TABLE, Node::isDisabled);
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