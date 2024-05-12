import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class TaskManagementApp extends Application {
    private TaskManager taskManager;

    @Override
    public void start(Stage primaryStage) {
        taskManager = new TaskManager();

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Calendar View
        GridPane calendarGrid = new GridPane();
        calendarGrid.setHgap(10);
        calendarGrid.setVgap(10);

        // Create some sample tasks
        Task task1 = new Task("Complete project", "Finish the project by the deadline", LocalDate.now(), Priority.HIGH, 0, "");
        Task task2 = new Task("Meeting with client", "Discuss project requirements", LocalDate.now(), Priority.MEDIUM, 0, "");
        Task task3 = new Task("Prepare presentation", "Create slides for the presentation", LocalDate.now(), Priority.LOW, 0, "");
        taskManager.scheduleTask(LocalDate.now(), task1);
        taskManager.scheduleTask(LocalDate.now(), task2);
        taskManager.scheduleTask(LocalDate.now(), task3);

        refreshCalendarView(calendarGrid, LocalDate.now());

        // Task List View
        ListView<Task> taskListView = new ListView<>();
        taskListView.setPrefWidth(300);
        taskListView.setItems(FXCollections.observableArrayList(taskManager.getTasks(LocalDate.now())));
        taskListView.setOnMouseClicked(event -> {
            Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                showTaskDetails(selectedTask);
            }
        });

        // Add/Delete Task Buttons
        Button addTaskButton = new Button("Add Task");
        addTaskButton.setOnAction(event -> addTask());

        Button deleteTaskButton = new Button("Delete Task");
        deleteTaskButton.setOnAction(event -> deleteTask(taskListView.getSelectionModel().getSelectedItem()));

        HBox buttonBox = new HBox(10, addTaskButton, deleteTaskButton);
        buttonBox.setPadding(new Insets(10));

        // Add components to root pane
        root.setLeft(calendarGrid);
        root.setRight(new VBox(10, buttonBox, taskListView));

        // Show scene
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Task Management App");
        primaryStage.show();
    }

    // Method to refresh the calendar view with tasks for the selected date
    private void refreshCalendarView(GridPane calendarGrid, LocalDate date) {
        calendarGrid.getChildren().clear();

        // Add calendar headers
        for (int i = 0; i < 24; i++) {
            Label timeLabel = new Label(String.format("%02d:00", i));
            calendarGrid.add(timeLabel, 0, i);
        }

        // Display tasks for the selected date
        List<Task> tasksForDate = taskManager.getTasks(date);
        for (Task task : tasksForDate) {
            int hour = task.getDueDate().getHour();
            int minute = task.getDueDate().getMinute();
            Label taskLabel = new Label(task.getTitle());
            taskLabel.setStyle("-fx-background-color: " + getPriorityColor(task.getPriority()) + "; -fx-padding: 5px;");
            calendarGrid.add(taskLabel, 1, hour * 2 + (minute / 30));
        }
    }

    // Method to handle adding a new task
    private void addTask() {
        // Implement logic to add a new task, e.g., open a dialog for user input
        // Once the task is added, refresh the task list and calendar view
    }

    // Method to handle deleting a task
    private void deleteTask(Task task) {
        if (task != null) {
            // Implement logic to delete the selected task
            // Once the task is deleted, refresh the task list and calendar view
        }
    }

    // Method to show task details in an alert dialog
    private void showTaskDetails(Task task) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task Details");
        alert.setHeaderText(task.getTitle());
        alert.setContentText("Description: " + task.getDescription() + "\nDue Date: " + task.getDueDate() +
                "\nPriority: " + task.getPriority() + "\nProgress: " + task.getProgress() + "\nNotes: " + task.getNotes());
        alert.showAndWait();
    }

    // Method to get priority color based on task priority
    private String getPriorityColor(Priority priority) {
        switch (priority) {
            case HIGH:
                return "red";
            case MEDIUM:
                return "yellow";
            case LOW:
                return "green";
            default:
                return "white";
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class TaskManager {
    private Map<LocalDate, List<Task>> tasksMap;

    public TaskManager() {
        tasksMap = new HashMap<>();
    }

    public void scheduleTask(LocalDate date, Task task) {
        tasksMap.computeIfAbsent(date, k -> new ArrayList<>()).add(task);
    }

    public List<Task> getTasks(LocalDate date) {
        return tasksMap.getOrDefault(date, Collections.emptyList());
    }
}

class Task {
    private String title;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
    private int progress;
    private String notes;

    public Task(String title, String description, LocalDate dueDate, Priority priority, int progress, String notes) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.progress = progress;
        this.notes = notes;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public int getProgress() {
        return progress;
    }

    public String getNotes() {
        return notes;
    }
}

enum Priority {
    HIGH,
    MEDIUM,
    LOW
}
