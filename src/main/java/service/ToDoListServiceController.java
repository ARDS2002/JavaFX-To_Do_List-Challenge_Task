package service;

import controller.ToDoListFormController;
import db.DBConnection;
import dto.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToDoListServiceController implements ToDoListService {
    private static ToDoListServiceController instance = null;

    private ToDoListServiceController() {
    }

    public static ToDoListServiceController getInstance() {
        if (instance == null) {
            instance = new ToDoListServiceController();
        }
        return instance;
    }

    public void addTask(Task newTask) {
        String SQL = "INSERT INTO active_task (TaskTitle, TaskDescription, Date, Time) VALUES (?, ?, ?, ?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstms = connection.prepareStatement(SQL);
            pstms.setString(1, newTask.getTaskTitle());
            pstms.setString(2, newTask.getTaskDescription());
            pstms.setDate(3, Date.valueOf(newTask.getDate()));
            pstms.setTime(4, Time.valueOf(newTask.getTime()));
            pstms.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void markCompletedTask(Task completedTask) {

        String SQL = "INSERT INTO completed_task (TaskId, TaskTitle, TaskDescription, Date, Time) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstms = connection.prepareStatement(SQL);
            pstms.setInt(1, completedTask.getTaskID());
            pstms.setString(2, completedTask.getTaskTitle());
            pstms.setString(3, completedTask.getTaskDescription());
            pstms.setDate(4, Date.valueOf(completedTask.getDate()));
            pstms.setTime(5, Time.valueOf(completedTask.getTime()));

            pstms.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        deleteCompletedTask(completedTask.getTaskTitle());
    }

    public void deleteCompletedTask(String taskTitle) {

        String SQL = "DELETE FROM active_task WHERE TaskTitle = ?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(SQL);
            pstm.setString(1, taskTitle);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Task> getAllActiveTask() {
        String SQL = "SELECT * FROM active_task ORDER BY TaskId ASC";
        List<Task> taskList = new ArrayList<>();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pStmt = connection.prepareStatement(SQL);
            ResultSet rts = pStmt.executeQuery();

            while (rts.next()) {

                Task task = new Task(
                        rts.getInt(1),
                        rts.getString(2),
                        rts.getString(3),
                        rts.getDate(4).toLocalDate(),
                        rts.getTime(5).toLocalTime());

                taskList.add(task);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return taskList;
    }

    public List<Task> getAllCompletedTask(String dbColumnName) {
        String SQL = "SELECT * FROM completed_task ORDER BY '" + dbColumnName + "' ASC";
        List<Task> taskList = new ArrayList<>();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pStmt = connection.prepareStatement(SQL);
            ResultSet rts = pStmt.executeQuery();

            while (rts.next()) {

                Task task = new Task(
                        rts.getInt(1),
                        rts.getString(2),
                        rts.getString(3),
                        rts.getDate(4).toLocalDate(),
                        rts.getTime(5).toLocalTime());

                taskList.add(task);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return taskList;
    }
}
