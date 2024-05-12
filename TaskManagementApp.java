// Main class
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Task Management App");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

// Controller class (sample.fxml and SampleController.fxml)
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class SampleController {

    @FXML
    private Button addTaskButton;

    @FXML
    private Button deleteTaskButton;

    @FXML
    private ListView<?> taskListView;

    @FXML
    void addTask(ActionEvent event) {
        // Logic to add a task
    }

    @FXML
    void deleteTask(ActionEvent event) {
        // Logic to delete a task
    }
}

// TaskManager class
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private Map<LocalDate, List<Task>> tasksMap;

    public TaskManager() {
        tasksMap = new HashMap<>();
    }

    public void scheduleTask(LocalDate date, Task task) {
        tasksMap.computeIfAbsent(date, k -> new ArrayList<>()).add(task);
    }

    public List<Task> getTasks(LocalDate date) {
        return tasksMap.getOrDefault(date, new ArrayList<>());
    }
}

// Task class
import java.time.LocalDate;

public class Task {
    private String title;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
    private int progress;
    private String notes;

    // Constructor, getters, and setters
}

// Priority enum
public enum Priority {
    HIGH,
    MEDIUM,
    LOW
}
