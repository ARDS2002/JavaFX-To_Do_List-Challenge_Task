package controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import dto.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.ToDoListService;
import service.ToDoListServiceController;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class ToDoListFormController implements Initializable {

    @FXML
    private DatePicker dateCurrentDate;

    @FXML
    private JFXListView<String> listTask;

    @FXML
    private JFXTextField txtAddTask;

    private ToDoListService toDoListService = ToDoListServiceController.getInstance();

    private Stage viewStageForm = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < toDoListService.getAllActiveTask().size(); i++) {
            items.add(toDoListService.getAllActiveTask().get(i).getTaskTitle());
        }
        listTask.setItems(items);

        dateCurrentDate.setValue(LocalDate.now());

        listTask.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

        });

        listTask.setCellFactory(lv -> new ListCell<String>() {
            private final CheckBox checkBox = new CheckBox();
            //private final Image img = new Image("img/trash-2.png");
            private final ButtonType btnDelete = new ButtonType("-");

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    checkBox.setText(item);
                    setGraphic(checkBox);
                    //setGraphic(img);

                    checkBox.setOnAction(event -> {
                        boolean selected = checkBox.isSelected();
                        if (checkBox.isSelected()) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
                            alert.setTitle("Confirmation");
                            alert.setHeaderText("Your Task: " + item.toUpperCase());
                            alert.setContentText("Are you sure, you completed the task?");

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.YES) {
                                //System.out.println(item);
                                for (int i = 0; i < toDoListService.getAllActiveTask().size(); i++) {
                                    if (toDoListService.getAllActiveTask().get(i).getTaskTitle().equalsIgnoreCase(item)) {
                                        //System.out.println(toDoListService.getAllActiveTask().get(i));
                                        toDoListService.markCompletedTask(toDoListService.getAllActiveTask().get(i));
                                        checkBox.setSelected(false);
                                        listTask.getItems().remove(item);
                                    }
                                }
                            } else {
                                checkBox.setSelected(false);
                                alert.close();
                            }
                        } else {
                            checkBox.setSelected(false);
                        }
                    });
                }
            }
        });

    }

    @FXML
    void imgAddOnMouseClick(MouseEvent event) {

        toDoListService.addTask(new Task(0, txtAddTask.getText(), null, LocalDate.now(), LocalTime.now()));
        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < toDoListService.getAllActiveTask().size(); i++) {
            items.add(toDoListService.getAllActiveTask().get(i).getTaskTitle());
        }
        listTask.setItems(items);
        listTask.refresh();
        txtAddTask.clear();

    }

    @FXML
    void txtAddTaskOnAction(ActionEvent event) {

        toDoListService.addTask(new Task(0, txtAddTask.getText(), null, LocalDate.now(), LocalTime.now()));
        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < toDoListService.getAllActiveTask().size(); i++) {
            items.add(toDoListService.getAllActiveTask().get(i).getTaskTitle());
        }
        listTask.setItems(items);
        listTask.refresh();
        txtAddTask.clear();

    }

    public void btnViewOnAction(ActionEvent actionEvent) {
        if (viewStageForm == null) {
            viewStageForm = new Stage();
            try {
                viewStageForm.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/completed_task_list_form.fxml"))));
                viewStageForm.setResizable(false);
                viewStageForm.setTitle("TO DO LIST FORM");
                viewStageForm.setOnCloseRequest(event -> viewStageForm = null);
                viewStageForm.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            viewStageForm.toFront();
        }
    }

    /*public void imgDeleteOnMouseClick(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Your Task: ");
        alert.setContentText("Are you sure, you want to delete the task?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {

        } else {

            alert.close();
        }
    }*/
}
