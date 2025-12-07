package com.lucam.to_do_list.service.impl;

import com.lucam.to_do_list.entity.Task;
import com.lucam.to_do_list.enums.Priority;
import com.lucam.to_do_list.enums.Status;
import com.lucam.to_do_list.exception.TaskNotFoundException;
import com.lucam.to_do_list.repository.TaskRepository;
import com.lucam.to_do_list.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public Task createTask(Task task) {
        log.info("Creando tarea con ID: {}", task.getId());
        Task newTask = taskRepository.save(task);
        log.info("Tarea creada exitosamente con ID: {}", newTask.getId());
        return newTask;
    }

    @Override
    @Transactional
    public Task updateTask(Long id, Task task) {
        log.info("Actualizando tarea con ID: {}", id);

        Task existingTask = taskRepository.findById(id).
                orElseThrow(() -> new TaskNotFoundException(id));


        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());
        existingTask.setPriority(task.getPriority());
        existingTask.setDueDate(task.getDueDate());

        Task updatedTask = taskRepository.save(existingTask);

        log.info("Tarea actualizada correctamente");

        return updatedTask;
    }

    @Override
    public List<Task> getAllTasks() {
        log.debug("Recopilando todas las tareas...");
        List<Task> listAllTasks = taskRepository.findAll();
        log.info("Todas las tareas han sido encontradas");
        return listAllTasks;
    }

    @Override
    public Task getTaskById(Long id) {
        log.debug("Encontrando tarea con ID: {}", id);

        return taskRepository.findById(id).
                orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    @Transactional
    public void deleteTaskbyId(Long id) {
        log.info("Eliminando tarea con ID: {}", id);

        if(!taskRepository.existsById(id)){
            throw new TaskNotFoundException(id);
        }

        taskRepository.deleteById(id);
        log.info("Tarea con ID: {}, eliminada correctamente", id);
    }

    @Override
    public List<Task> findTasksByStatus(Status status) {
        log.debug("Buscando tareas con estado '{}'", status);
        return taskRepository.findByStatus(status);
    }

    @Override
    public List<Task> findTasksByPriority(Priority priority) {
        log.debug("Buscando tareas con priridad '{}'", priority);
        return taskRepository.findByPriority(priority);
    }

    @Override
    public List<Task> findTasksByStatusAndPriority(Status status, Priority priority) {
        log.debug("Buscando tareas con estado '{}' y prioridad '{}'", status, priority);
        return taskRepository.findByStatusAndPriority(status, priority);
    }

    @Override
    public List<Task> findTasksByRangeDueDate(LocalDate startDate, LocalDate endDate) {
        log.debug("Buscando tareas entre las fechas {} y {}", startDate, endDate);
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La fecha inicial no puede ser posterior a la fecha final");
        }
        return taskRepository.findByDueDateBetween(startDate, endDate);
    }

    @Override
    public List<Task> findTasksOverdue() {
        log.debug("Buscando tareas vencidas");
        return taskRepository.findOverdueTasks(LocalDate.now());
    }

    @Override
    public List<Task> findTasksByDueDate() {
        log.debug("Buscando tareas que vencen hoy");
        return taskRepository.findTasksDueToday(LocalDate.now());
    }

    @Override
    public List<Task> findBySearchTerm(String searchTerm) {
        log.debug("Buscando tareas con termino: {}", searchTerm);

        if(searchTerm == null || searchTerm.trim().isEmpty()) {
            throw new IllegalArgumentException("El termino no puede estar vacio");
        }
        return taskRepository.searchByTitleOrDescription(searchTerm);
    }

    @Override
    public Long countTasksByStatus(Status status) {
        log.debug("Contadno tareas por estado: {}", status);
        return taskRepository.countByStatus(status);
    }

    @Override
    @Transactional
    public Task markTaskAsInProgress(Long id) {
        log.info("Marcando tarea {} como 'En Progreso'", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.markAsInProgress();

        Task updatedTask = taskRepository.save(task);
        log.info("Tarea marcada exitosamente");
        return updatedTask;
    }

    @Override
    @Transactional
    public Task markTaskAsCompleted(Long id) {

        log.info("Marcando tarea {} como completada", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.markAsCompleted();

        Task updatedTask = taskRepository.save(task);

        log.info("Tarea marcada como completada exitosamente");
        return updatedTask;
    }
}