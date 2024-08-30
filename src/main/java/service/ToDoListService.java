package service;

import dto.Task;

import java.util.List;

public interface ToDoListService {

    void addTask(Task newTask);

    void markCompletedTask(Task completedTask);

    List<Task> getAllActiveTask();

    List<Task> getAllCompletedTask(String dbColumnName);

}
