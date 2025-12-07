package com.lucam.to_do_list.controller;

import com.lucam.to_do_list.entity.Task;
import com.lucam.to_do_list.enums.Priority;
import com.lucam.to_do_list.enums.Status;
import com.lucam.to_do_list.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask (@Valid @RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTaskbyId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTaskByStatus(@PathVariable Status status) {
        List<Task> tasks = taskService.findTasksByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable Priority priority){
        List<Task> tasks = taskService.findTasksByPriority(priority);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Task>> getTasksByStatusAndPriority(@RequestParam Status status, @RequestParam Priority priority) {
        List<Task> tasks = taskService.findTasksByStatusAndPriority(status, priority);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/due-date-range")
    public ResponseEntity<List<Task>> getTasksByDueDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ){
        List<Task> tasks = taskService.findTasksByRangeDueDate(startDate, endDate);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Task>> getOverdueTasks() {
        List<Task> tasks = taskService.findTasksOverdue();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/due-today")
    public ResponseEntity<List<Task>> getTasksDueToday() {
        List<Task> tasks = taskService.findTasksByDueDate();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchTasks(@RequestParam String term){
        List<Task> tasks = taskService.findBySearchTerm(term);
        return ResponseEntity.ok(tasks);
    }

    @PatchMapping("/{id}/in-progress")
    public ResponseEntity<Task> markTaskAsInProgress(@PathVariable Long id){
        Task updatedTask = taskService.markTaskAsInProgress(id);
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Task> markTaskAsCompleted(@PathVariable Long id){
        Task completedTask = taskService.markTaskAsCompleted(id);
        return ResponseEntity.ok(completedTask);
    }

    @GetMapping("/count/status/{status}")
    public ResponseEntity<Long> countTasksByStatus(@PathVariable Status status) {
        Long count = taskService.countTasksByStatus(status);
        return ResponseEntity.ok(count);
    }

}
