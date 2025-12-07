package com.lucam.to_do_list.service;

import com.lucam.to_do_list.entity.Task;
import com.lucam.to_do_list.enums.Priority;
import com.lucam.to_do_list.enums.Status;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {

    Task createTask(Task task);

    Task updateTask(Long id, Task task);

    List<Task> getAllTasks();

    Task getTaskById(Long id);

    void deleteTaskbyId(Long id);

    List<Task> findTasksByStatus(Status status);

    List<Task> findTasksByPriority(Priority priority);

    List<Task> findTasksByStatusAndPriority(Status status, Priority priority);

    List<Task> findTasksByRangeDueDate(LocalDate startDate, LocalDate endDate);

    List<Task> findTasksOverdue();

    List<Task> findTasksByDueDate();

    List<Task> findBySearchTerm(String searchTerm);

    Long countTasksByStatus(Status status);

    Task markTaskAsInProgress(Long id);

    Task markTaskAsCompleted(Long id);

}