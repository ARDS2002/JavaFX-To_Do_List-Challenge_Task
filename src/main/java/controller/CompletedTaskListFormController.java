package controller;

import db.DBConnection;
import dto.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.ToDoListService;
import service.ToDoListServiceController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CompletedTaskListFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colTaskDate;

    @FXML
    private TableColumn<?, ?> colTaskDescription;

    @FXML
    private TableColumn<?, ?> colTaskTime;

    @FXML
    private TableColumn<?, ?> colTaskTitle;

    @FXML
    private TableView<Task> tblCompletedTask;

    ToDoListService toDoListService = ToDoListServiceController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTaskTitle.setCellValueFactory(new PropertyValueFactory<>("taskTitle"));
        colTaskDescription.setCellValueFactory(new PropertyValueFactory<>("taskDescription"));
        colTaskDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTaskTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        loadTable();
    }

    private void loadTable() {
        List<Task> taskList = toDoListService.getAllCompletedTask("TaskId");
        ObservableList<Task> taskObservableList = FXCollections.observableArrayList();

        taskObservableList.addAll(taskList);

        tblCompletedTask.setItems(taskObservableList);
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadTable();
        tblCompletedTask.refresh();
    }

    public void btnSortByDateOnAction(ActionEvent actionEvent) {
        List<Task> taskList = toDoListService.getAllCompletedTask("Date");
        ObservableList<Task> taskObservableList = FXCollections.observableArrayList();

        taskObservableList.addAll(taskList);

        tblCompletedTask.setItems(taskObservableList);
    }
}
